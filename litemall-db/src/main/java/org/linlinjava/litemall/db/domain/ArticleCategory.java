package org.linlinjava.litemall.db.domain;


public class ArticleCategory {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_category.category_id
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    private Integer categoryId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_category.name
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_category.status
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    private Byte status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_category.is_view
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    private Byte isView;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_category.list_photo_url
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    private String listPhotoUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_category.create_date
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    private String createDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_category.category_id
     *
     * @return the value of article_category.category_id
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_category.category_id
     *
     * @param categoryId the value for article_category.category_id
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_category.name
     *
     * @return the value of article_category.name
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_category.name
     *
     * @param name the value for article_category.name
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_category.status
     *
     * @return the value of article_category.status
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_category.status
     *
     * @param status the value for article_category.status
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_category.is_view
     *
     * @return the value of article_category.is_view
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public Byte getIsView() {
        return isView;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_category.is_view
     *
     * @param isView the value for article_category.is_view
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public void setIsView(Byte isView) {
        this.isView = isView;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_category.list_photo_url
     *
     * @return the value of article_category.list_photo_url
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public String getListPhotoUrl() {
        return listPhotoUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_category.list_photo_url
     *
     * @param listPhotoUrl the value for article_category.list_photo_url
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public void setListPhotoUrl(String listPhotoUrl) {
        this.listPhotoUrl = listPhotoUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_category.create_date
     *
     * @return the value of article_category.create_date
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_category.create_date
     *
     * @param createDate the value for article_category.create_date
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_category
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", categoryId=").append(categoryId);
        sb.append(", name=").append(name);
        sb.append(", status=").append(status);
        sb.append(", isView=").append(isView);
        sb.append(", listPhotoUrl=").append(listPhotoUrl);
        sb.append(", createDate=").append(createDate);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_category
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
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
        ArticleCategory other = (ArticleCategory) that;
        return (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIsView() == null ? other.getIsView() == null : this.getIsView().equals(other.getIsView()))
            && (this.getListPhotoUrl() == null ? other.getListPhotoUrl() == null : this.getListPhotoUrl().equals(other.getListPhotoUrl()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_category
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIsView() == null) ? 0 : getIsView().hashCode());
        result = prime * result + ((getListPhotoUrl() == null) ? 0 : getListPhotoUrl().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table article_category
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        categoryId("category_id"),
        name("name"),
        status("status"),
        isView("is_view"),
        listPhotoUrl("list_photo_url"),
        createDate("create_date");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table article_category
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table article_category
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table article_category
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table article_category
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column) {
            this.column = column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table article_category
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.column + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table article_category
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.column + " ASC";
        }
    }
}