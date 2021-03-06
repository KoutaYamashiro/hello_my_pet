package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")  // 全てに適応
public class LoginFilter implements Filter {

    /**
     * Default constructor.
     */
    public LoginFilter() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String context_path = ((HttpServletRequest)request).getContextPath();
        String servlet_path = ((HttpServletRequest)request).getServletPath();

        if(!servlet_path.matches("/css.*")) {       // CSSフォルダ内は認証処理から除外する

            HttpSession session = ((HttpServletRequest)request).getSession();
            // セッションスコープに保存されたユーザー（ログインユーザ）情報を取得
            User u = (User)session.getAttribute("login_user");

            if(!servlet_path.equals("/login")) {  // ログイン画面以外について
                // ユーザーページについて
                // 管理者のみがユーザー一覧ページを表示できるようにする
                if(servlet_path.matches("/users/index") && (u == null || u.getAdmin_flag() == 0)) {
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    return;
                }

                // 未ログインユーザについて
                // 未ログインユーザーはユーザー詳細、ユーザー更新ページを表示できない
                if((servlet_path.matches("/users/show") || (servlet_path.matches("/users/edit"))) && u == null) {
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    return;
                }
                // 未ログインユーザーはお問い合わせページ、返信ページを表示できない
                if((servlet_path.matches("/contacts.* ") || (servlet_path.matches("/replies.* "))) && u == null) {
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    return;
                }
                // 未ログインユーザーはFavoritesページを表示できない
                if(servlet_path.matches("/favorites.* ") && u == null) {
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    return;
                }

                //ペットページについて
                // 管理者のみがペット一覧を表示できるようにする
                if((servlet_path.matches("/pets/index") || (servlet_path.matches("/pets/new") || (servlet_path.matches("/pets/edit"))))
                        && (u == null || u.getAdmin_flag() == 0)) {
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    return;
                }

            } else {  // ログイン画面について
                // ログインしているのにログイン画面を表示させようとした場合は
                // システムのトップページにリダイレクト
                if(u != null) {
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }

}