package pages;

import com.microsoft.playwright.Page;
import pages.component.LeftPane;

public class DashboardPage {

    private Page page;

    private String violations_graph = "div[data-test='apex-area-chart']";
    private LeftPane leftPane = new LeftPane();
    public DashboardPage(Page page) {
        this.page = page;
    }
    public Boolean isPageLoaded() {
        page.waitForSelector(violations_graph);
        return page.isVisible(violations_graph);
    }

    public SlackPolicyPage navigateToSlackPolicyPage() {
        page.click(leftPane.slackPolicy_btn);
        return new SlackPolicyPage(page);
    }
}
