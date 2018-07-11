package org.linlinjava.litemall.db.domain;

import java.math.BigDecimal;
import java.util.Date;

public class LitemallGoods {
    private Integer id;

    private String goodsSn;

    private String name;

    private Integer categoryId;

    private Integer brandId;

    private String gallery;

    private String keywords;

    private String goodsBrief;

    private Boolean isOnSale;

    private Short sortOrder;

    private BigDecimal counterPrice;

    private Boolean isNew;

    private String primaryPicUrl;

    private String listPicUrl;

    private Boolean isHot;

    private String goodsUnit;

    private BigDecimal retailPrice;

    private Date addTime;

    private Boolean deleted;

    private Date startDate;

    private Date endDate;

    private Integer integretion;

    private Integer personNum;

    private String content;

    private String memo;

    private Integer status;

    private Date createDate;

    private Integer integretions;

    private String goodsDesc;

    private Integer isexist;

    private Integer state;

    private Integer type;

    private Integer sortNo;

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsexist() {
        return isexist;
    }

    public void setIsexist(Integer isexist) {
        this.isexist = isexist;
    }

    public Integer getIntegretions() {
        return integretions;
    }

    public void setIntegretions(Integer integretions) {
        this.integretions = integretions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getGoodsBrief() {
        return goodsBrief;
    }

    public void setGoodsBrief(String goodsBrief) {
        this.goodsBrief = goodsBrief;
    }

    public Boolean getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Boolean isOnSale) {
        this.isOnSale = isOnSale;
    }

    public Short getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Short sortOrder) {
        this.sortOrder = sortOrder;
    }

    public BigDecimal getCounterPrice() {
        return counterPrice;
    }

    public void setCounterPrice(BigDecimal counterPrice) {
        this.counterPrice = counterPrice;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public String getPrimaryPicUrl() {
        return primaryPicUrl;
    }

    public void setPrimaryPicUrl(String primaryPicUrl) {
        this.primaryPicUrl = primaryPicUrl;
    }

    public String getListPicUrl() {
        return listPicUrl;
    }

    public void setListPicUrl(String listPicUrl) {
        this.listPicUrl = listPicUrl;
    }

    public Boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getIntegretion() {
        return integretion;
    }

    public void setIntegretion(Integer integretion) {
        this.integretion = integretion;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", goodsSn=").append(goodsSn);
        sb.append(", name=").append(name);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", brandId=").append(brandId);
        sb.append(", gallery=").append(gallery);
        sb.append(", keywords=").append(keywords);
        sb.append(", goodsBrief=").append(goodsBrief);
        sb.append(", isOnSale=").append(isOnSale);
        sb.append(", sortOrder=").append(sortOrder);
        sb.append(", counterPrice=").append(counterPrice);
        sb.append(", isNew=").append(isNew);
        sb.append(", primaryPicUrl=").append(primaryPicUrl);
        sb.append(", listPicUrl=").append(listPicUrl);
        sb.append(", isHot=").append(isHot);
        sb.append(", goodsUnit=").append(goodsUnit);
        sb.append(", retailPrice=").append(retailPrice);
        sb.append(", addTime=").append(addTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", integretion=").append(integretion);
        sb.append(", personNum=").append(personNum);
        sb.append(", content=").append(content);
        sb.append(", memo=").append(memo);
        sb.append(", status=").append(status);
        sb.append(", createDate=").append(createDate);
        sb.append(", goodsDesc=").append(goodsDesc);
        sb.append(", state=").append(state);
        sb.append(", type=").append(type);
        sb.append(", sortNo=").append(sortNo);
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
        LitemallGoods other = (LitemallGoods) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGoodsSn() == null ? other.getGoodsSn() == null : this.getGoodsSn().equals(other.getGoodsSn()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getBrandId() == null ? other.getBrandId() == null : this.getBrandId().equals(other.getBrandId()))
            && (this.getGallery() == null ? other.getGallery() == null : this.getGallery().equals(other.getGallery()))
            && (this.getKeywords() == null ? other.getKeywords() == null : this.getKeywords().equals(other.getKeywords()))
            && (this.getGoodsBrief() == null ? other.getGoodsBrief() == null : this.getGoodsBrief().equals(other.getGoodsBrief()))
            && (this.getIsOnSale() == null ? other.getIsOnSale() == null : this.getIsOnSale().equals(other.getIsOnSale()))
            && (this.getSortOrder() == null ? other.getSortOrder() == null : this.getSortOrder().equals(other.getSortOrder()))
            && (this.getCounterPrice() == null ? other.getCounterPrice() == null : this.getCounterPrice().equals(other.getCounterPrice()))
            && (this.getIsNew() == null ? other.getIsNew() == null : this.getIsNew().equals(other.getIsNew()))
            && (this.getPrimaryPicUrl() == null ? other.getPrimaryPicUrl() == null : this.getPrimaryPicUrl().equals(other.getPrimaryPicUrl()))
            && (this.getListPicUrl() == null ? other.getListPicUrl() == null : this.getListPicUrl().equals(other.getListPicUrl()))
            && (this.getIsHot() == null ? other.getIsHot() == null : this.getIsHot().equals(other.getIsHot()))
            && (this.getGoodsUnit() == null ? other.getGoodsUnit() == null : this.getGoodsUnit().equals(other.getGoodsUnit()))
            && (this.getRetailPrice() == null ? other.getRetailPrice() == null : this.getRetailPrice().equals(other.getRetailPrice()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
            && (this.getStartDate() == null ? other.getStartDate() == null : this.getStartDate().equals(other.getStartDate()))
            && (this.getEndDate() == null ? other.getEndDate() == null : this.getEndDate().equals(other.getEndDate()))
            && (this.getIntegretion() == null ? other.getIntegretion() == null : this.getIntegretion().equals(other.getIntegretion()))
            && (this.getPersonNum() == null ? other.getPersonNum() == null : this.getPersonNum().equals(other.getPersonNum()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getMemo() == null ? other.getMemo() == null : this.getMemo().equals(other.getMemo()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getGoodsDesc() == null ? other.getGoodsDesc() == null : this.getGoodsDesc().equals(other.getGoodsDesc()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGoodsSn() == null) ? 0 : getGoodsSn().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getBrandId() == null) ? 0 : getBrandId().hashCode());
        result = prime * result + ((getGallery() == null) ? 0 : getGallery().hashCode());
        result = prime * result + ((getKeywords() == null) ? 0 : getKeywords().hashCode());
        result = prime * result + ((getGoodsBrief() == null) ? 0 : getGoodsBrief().hashCode());
        result = prime * result + ((getIsOnSale() == null) ? 0 : getIsOnSale().hashCode());
        result = prime * result + ((getSortOrder() == null) ? 0 : getSortOrder().hashCode());
        result = prime * result + ((getCounterPrice() == null) ? 0 : getCounterPrice().hashCode());
        result = prime * result + ((getIsNew() == null) ? 0 : getIsNew().hashCode());
        result = prime * result + ((getPrimaryPicUrl() == null) ? 0 : getPrimaryPicUrl().hashCode());
        result = prime * result + ((getListPicUrl() == null) ? 0 : getListPicUrl().hashCode());
        result = prime * result + ((getIsHot() == null) ? 0 : getIsHot().hashCode());
        result = prime * result + ((getGoodsUnit() == null) ? 0 : getGoodsUnit().hashCode());
        result = prime * result + ((getRetailPrice() == null) ? 0 : getRetailPrice().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
        result = prime * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
        result = prime * result + ((getIntegretion() == null) ? 0 : getIntegretion().hashCode());
        result = prime * result + ((getPersonNum() == null) ? 0 : getPersonNum().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getMemo() == null) ? 0 : getMemo().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getGoodsDesc() == null) ? 0 : getGoodsDesc().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id"),
        goodsSn("goods_sn"),
        name("name"),
        categoryId("category_id"),
        brandId("brand_id"),
        gallery("gallery"),
        keywords("keywords"),
        goodsBrief("goods_brief"),
        isOnSale("is_on_sale"),
        sortOrder("sort_order"),
        counterPrice("counter_price"),
        isNew("is_new"),
        primaryPicUrl("primary_pic_url"),
        listPicUrl("list_pic_url"),
        isHot("is_hot"),
        goodsUnit("goods_unit"),
        retailPrice("retail_price"),
        addTime("add_time"),
        deleted("deleted"),
        startDate("start_date"),
        endDate("end_date"),
        integretion("integretion"),
        personNum("person_num"),
        content("content"),
        memo("memo"),
        status("status"),
        createDate("create_date"),
        goodsDesc("goods_desc"),
        state("state"),
        type("type"),
        sortNo("sort_no");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_goods
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column) {
            this.column = column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.column + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.column + " ASC";
        }
    }
}