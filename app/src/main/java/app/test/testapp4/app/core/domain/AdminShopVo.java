package app.test.testapp4.app.core.domain;

import java.util.List;

/**
 * Created by BIT on 2017-01-16.
 */

public class AdminShopVo {
    private Long total_block;
    private Long after_block;
    private Long current_block;
    private Long before_block;
    private Long begin_block;
    private List<ShopVo> list;
    private Long end_block;

    public Long getTotal_block() {
        return total_block;
    }

    public void setTotal_block(Long total_block) {
        this.total_block = total_block;
    }

    public Long getAfter_block() {
        return after_block;
    }

    public void setAfter_block(Long after_block) {
        this.after_block = after_block;
    }

    public Long getCurrent_block() {
        return current_block;
    }

    public void setCurrent_block(Long current_block) {
        this.current_block = current_block;
    }

    public Long getBefore_block() {
        return before_block;
    }

    public void setBefore_block(Long before_block) {
        this.before_block = before_block;
    }

    public Long getBegin_block() {
        return begin_block;
    }

    public void setBegin_block(Long begin_block) {
        this.begin_block = begin_block;
    }

    public List<ShopVo> getList() {
        return list;
    }

    public void setList(List<ShopVo> list) {
        this.list = list;
    }

    public Long getEnd_block() {
        return end_block;
    }

    public void setEnd_block(Long end_block) {
        this.end_block = end_block;
    }

    @Override
    public String toString() {
        return "AdminShopVo{" +
                "total_block=" + total_block +
                ", after_block=" + after_block +
                ", current_block=" + current_block +
                ", before_block=" + before_block +
                ", begin_block=" + begin_block +
                ", list=" + list +
                ", end_block=" + end_block +
                '}';
    }
}
