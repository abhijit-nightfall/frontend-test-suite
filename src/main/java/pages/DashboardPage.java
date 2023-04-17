package pages;

import com.microsoft.playwright.Page;

public class DashboardPage {

    private Page page;

    private String violations_graph = "div[data-test='apex-area-chart']";
    public DashboardPage(Page page) {
        this.page = page;
    }
    public Boolean isViolationsGraphVisible() {
        return page.isVisible(violations_graph);
    }
}
