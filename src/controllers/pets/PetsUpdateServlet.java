package controllers.pets;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import models.Pet;
import models.User;
import models.validators.PetValidator;
import utils.DBUtil;

/**
 * Servlet implementation class PetsUpdateServlet
 */
@WebServlet("/pets/update")
@MultipartConfig// 画像アップ機能時は必ず必要
public class PetsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PetsUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //partという変数を作る
        Part part = request.getPart("file");
        //part(主にjsp)から送られてきたファイル名を取得
        String name = this.getFileName(part);
        String fileName = getServletContext().getRealPath("/uploaded") + "/" + name;
        System.out.println("File Path : " + fileName);
        part.write(fileName);

        /* S3 */
        String region = (String) this.getServletContext().getAttribute("region");
        String awsAccessKey = (String) this.getServletContext().getAttribute("awsAccessKey");
        String awsSecretKey = (String) this.getServletContext().getAttribute("awsSecretKey");
        String bucketName = (String) this.getServletContext().getAttribute("bucketName");

        // 認証情報を用意
        AWSCredentials credentials = new BasicAWSCredentials(
                // アクセスキー
                awsAccessKey,
                // シークレットキー
                awsSecretKey);
        // クライアントを生成
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                // 認証情報を設定
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                // リージョンを AP_NORTHEAST_1(東京) に設定
                .withRegion(region).build();
        // === ファイルから直接アップロードする場合 ===
        // アップロードするファイル
        File file = new File(fileName);
        // ファイルをアップロード
        s3.putObject(
                // アップロード先バケット名
                bucketName,
                // アップロード後のキー名
                "uploaded/" + name,
                // ファイルの実体
                file);

        // String型の _tokenにパラメーターの_tokenを代入する
        String _token = (String) request.getParameter("_token");
        // _tokenがnullではなく、且つセッションIDと等しいならば
        if (_token != null && _token.equals(request.getSession().getId())) {
            // DAOインスタンスの生成
            EntityManager em = DBUtil.createEntityManager();

            // セッションスコープからペットのIDを取得して
            // 該当のIDのペット情報1件のみをデータベースから取得
            Pet p = em.find(Pet.class, (Integer) (request.getSession().getAttribute("pet_id")));

            // 変数pに現在ログインしているユーザーの情報をセットする
            p.setUser((User) request.getSession().getAttribute("login_user"));
            // 変数pに画像名をセットする
            p.setPet_image(name);
            // 変数pに入力したペット種類をセットする
            p.setPet_breed(request.getParameter("pet_breed"));
            // 変数pに選択した誕生日をセットする
            p.setBirthday(Date.valueOf(request.getParameter("birthday")));
            // 変数pに入力した生体価格をセットする
            p.setPet_price(request.getParameter("pet_price"));
            // 変数pに入力した内容をセットする
            p.setAppeal_point(request.getParameter("appeal_point"));
            // 変数pにDelete_flag０をセットする
            p.setDelete_flag(0);
            //  変数pに更新日時のみ上書き
            p.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            // バリデーター の呼び出し
            List<String> errors = PetValidator.validate(p);
            // errorsリストに1つでも追加されていたら
            if (errors.size() > 0) {
                // DAOの破棄
                em.close();

                // ペット情報とセッションID、エラー情報をリクエストスコープに登録
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("pet", p);
                request.setAttribute("errors", errors);
                // 画面遷移
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/pets/edit.jsp");
                rd.forward(request, response);
            } else {
                // データベースを更新
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                // セッションスコープにフラッシュメッセージをセットする
                request.getSession().setAttribute("flush", "更新が完了しました。");
                // セッションスコープ上の不要になったデータを削除
                request.getSession().removeAttribute("pet_id");
                // indexページへリダイレクト
                response.sendRedirect(request.getContextPath() + "/pets/index");
            }
        }
    }
    // 拡張子を変えずに、ランダムな名前のファイルを生成する
    private String getFileName(Part part) {
        String name = null;
        for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
            if (dispotion.trim().startsWith("filename")) {
                name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
                name = name.substring(name.lastIndexOf("\\") + 1);
                break;
            }
        }
        return name;
    }

}
