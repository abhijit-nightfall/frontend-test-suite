package pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    private Page page;

    private String email_txt = "input[id='1-email']";
    private String password_txt = "input[name='password']";
    private String acceptAndLogin_btn = "button[type='submit']";

    public LoginPage(Page page) {
        this.page = page;
    }

    public DashboardPage login(String username, String password) {
        page.fill(email_txt, username);
        page.fill(password_txt, password);
        page.click(acceptAndLogin_btn);
        return new DashboardPage(page);
    }

}
