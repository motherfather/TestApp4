package app.test.testapp4.app.core.domain;

/**
 * Created by BIT on 2017-01-16.
 */

public class SearchVo {
    private int page;
    private int filterCheck;
    private String keyword;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getFilterCheck() {
        return filterCheck;
    }

    public void setFilterCheck(int filterCheck) {
        this.filterCheck = filterCheck;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "SearchVo{" +
                "page=" + page +
                ", filterCheck=" + filterCheck +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
