package pages;

import com.microsoft.playwright.Page;

public class SlackPolicyPage {

    private Page page;

    private String slackHeading_txt = "//h2[normalize-space()='Slack']";
    public SlackPolicyPage(Page page) {
        this.page = page;
    }

    public boolean isPageLoaded() {
        page.waitForSelector(slackHeading_txt);
        return page.isVisible(slackHeading_txt);
    }

}
