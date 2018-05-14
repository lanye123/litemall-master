package org.linlinjava.litemall.db.domain;

import java.time.LocalDateTime;

public class ArticleNotes {
    private Integer id;

    private Integer artileId;

    private String name;

    private String no;

    private LocalDateTime createDate;

    private Integer sortNo;

    private Byte status;

    private String brief;

    private String articleName;

    private String daodu;

    private String author;

    private String photoUrl;

    private String photoName;

    private Integer render;

    private String content;

    private Integer readCount;

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArtileId() {
        return artileId;
    }

    public void setArtileId(Integer artileId) {
        this.artileId = artileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getDaodu() {
        return daodu;
    }

    public void setDaodu(String daodu) {
        this.daodu = daodu;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public Integer getRender() {
        return render;
    }

    public void setRender(Integer render) {
        this.render = render;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", artileId=").append(artileId);
        sb.append(", name=").append(name);
        sb.append(", no=").append(no);
        sb.append(", createDate=").append(createDate);
        sb.append(", sortNo=").append(sortNo);
        sb.append(", status=").append(status);
        sb.append(", brief=").append(brief);
        sb.append(", articleName=").append(articleName);
        sb.append(", daodu=").append(daodu);
        sb.append(", author=").append(author);
        sb.append(", photoUrl=").append(photoUrl);
        sb.append(", photoName=").append(photoName);
        sb.append(", render=").append(render);
        sb.append(", content=").append(content);
        sb.append(", readCount=").append(readCount);
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
        ArticleNotes other = (ArticleNotes) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getArtileId() == null ? other.getArtileId() == null : this.getArtileId().equals(other.getArtileId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getNo() == null ? other.getNo() == null : this.getNo().equals(other.getNo()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getSortNo() == null ? other.getSortNo() == null : this.getSortNo().equals(other.getSortNo()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getBrief() == null ? other.getBrief() == null : this.getBrief().equals(other.getBrief()))
            && (this.getArticleName() == null ? other.getArticleName() == null : this.getArticleName().equals(other.getArticleName()))
            && (this.getDaodu() == null ? other.getDaodu() == null : this.getDaodu().equals(other.getDaodu()))
            && (this.getAuthor() == null ? other.getAuthor() == null : this.getAuthor().equals(other.getAuthor()))
            && (this.getPhotoUrl() == null ? other.getPhotoUrl() == null : this.getPhotoUrl().equals(other.getPhotoUrl()))
            && (this.getPhotoName() == null ? other.getPhotoName() == null : this.getPhotoName().equals(other.getPhotoName()))
            && (this.getRender() == null ? other.getRender() == null : this.getRender().equals(other.getRender()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getArtileId() == null) ? 0 : getArtileId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getNo() == null) ? 0 : getNo().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getSortNo() == null) ? 0 : getSortNo().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getBrief() == null) ? 0 : getBrief().hashCode());
        result = prime * result + ((getArticleName() == null) ? 0 : getArticleName().hashCode());
        result = prime * result + ((getDaodu() == null) ? 0 : getDaodu().hashCode());
        result = prime * result + ((getAuthor() == null) ? 0 : getAuthor().hashCode());
        result = prime * result + ((getPhotoUrl() == null) ? 0 : getPhotoUrl().hashCode());
        result = prime * result + ((getPhotoName() == null) ? 0 : getPhotoName().hashCode());
        result = prime * result + ((getRender() == null) ? 0 : getRender().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table article_notes
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id"),
        artileId("artile_id"),
        name("name"),
        no("no"),
        createDate("create_date"),
        sortNo("sort_no"),
        status("status"),
        brief("brief"),
        articleName("article_name"),
        daodu("daodu"),
        author("author"),
        photoUrl("photo_url"),
        photoName("photo_name"),
        render("render"),
        content("content"),
        readCount("read_count");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table article_notes
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table article_notes
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table article_notes
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table article_notes
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column) {
            this.column = column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table article_notes
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.column + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table article_notes
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.column + " ASC";
        }
    }
}