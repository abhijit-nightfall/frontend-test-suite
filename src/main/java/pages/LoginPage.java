package pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    private Page page;

    private String email_txt = "input[id='1-email']";
    private String password_txt = "input[name='password']";
    private String acceptAndLogin_btn = "button[type='submit']";
    private String violations_graph = "div[data-test='apex-area-chart']";

    public LoginPage(Page page) {
        this.page = page;
    }

    public boolean login(String username, String password) {
        page.fill(email_txt, username);
        page.fill(password_txt, password);
        page.click(acceptAndLogin_btn);
        page.waitForSelector(violations_graph);
        return page.isVisible(violations_graph);
    }
}
