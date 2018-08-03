package org.linlinjava.litemall.db.domain;

import java.util.Date;

public class WxTemplete {
    private Integer id;

    private String type;

    private String templeteId;

    private String title;

    private String primaryIndustry;

    private String deputyIndustry;

    private String content;

    private String example;

    private Date createDate;

    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTempleteId() {
        return templeteId;
    }

    public void setTempleteId(String templeteId) {
        this.templeteId = templeteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrimaryIndustry() {
        return primaryIndustry;
    }

    public void setPrimaryIndustry(String primaryIndustry) {
        this.primaryIndustry = primaryIndustry;
    }

    public String getDeputyIndustry() {
        return deputyIndustry;
    }

    public void setDeputyIndustry(String deputyIndustry) {
        this.deputyIndustry = deputyIndustry;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", templeteId=").append(templeteId);
        sb.append(", title=").append(title);
        sb.append(", primaryIndustry=").append(primaryIndustry);
        sb.append(", deputyIndustry=").append(deputyIndustry);
        sb.append(", content=").append(content);
        sb.append(", example=").append(example);
        sb.append(", createDate=").append(createDate);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        WxTemplete other = (WxTemplete) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getTempleteId() == null ? other.getTempleteId() == null : this.getTempleteId().equals(other.getTempleteId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getPrimaryIndustry() == null ? other.getPrimaryIndustry() == null : this.getPrimaryIndustry().equals(other.getPrimaryIndustry()))
            && (this.getDeputyIndustry() == null ? other.getDeputyIndustry() == null : this.getDeputyIndustry().equals(other.getDeputyIndustry()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getExample() == null ? other.getExample() == null : this.getExample().equals(other.getExample()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getTempleteId() == null) ? 0 : getTempleteId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getPrimaryIndustry() == null) ? 0 : getPrimaryIndustry().hashCode());
        result = prime * result + ((getDeputyIndustry() == null) ? 0 : getDeputyIndustry().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getExample() == null) ? 0 : getExample().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table wx_templete
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id"),
        type("type"),
        templeteId("templete_id"),
        title("title"),
        primaryIndustry("primary_industry"),
        deputyIndustry("deputy_industry"),
        content("content"),
        example("example"),
        createDate("create_date"),
        deleted("deleted");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table wx_templete
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table wx_templete
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table wx_templete
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table wx_templete
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column) {
            this.column = column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table wx_templete
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.column + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table wx_templete
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.column + " ASC";
        }
    }
}