package org.linlinjava.litemall.db.domain;

import java.time.LocalDateTime;

public class IntegretionDetail {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column integretion_detail.id
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column integretion_detail.integretion_id
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    private String integretionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column integretion_detail.user_id
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    private String userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column integretion_detail.amount
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    private Integer amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column integretion_detail.status
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    private Byte status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column integretion_detail.type
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    private Byte type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column integretion_detail.create_date
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    private LocalDateTime createDate;

    private String start_date;
    private String end_date;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column integretion_detail.id
     *
     * @return the value of integretion_detail.id
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column integretion_detail.id
     *
     * @param id the value for integretion_detail.id
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column integretion_detail.integretion_id
     *
     * @return the value of integretion_detail.integretion_id
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public String getIntegretionId() {
        return integretionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column integretion_detail.integretion_id
     *
     * @param integretionId the value for integretion_detail.integretion_id
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public void setIntegretionId(String integretionId) {
        this.integretionId = integretionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column integretion_detail.user_id
     *
     * @return the value of integretion_detail.user_id
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column integretion_detail.user_id
     *
     * @param userId the value for integretion_detail.user_id
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column integretion_detail.amount
     *
     * @return the value of integretion_detail.amount
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column integretion_detail.amount
     *
     * @param amount the value for integretion_detail.amount
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column integretion_detail.status
     *
     * @return the value of integretion_detail.status
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column integretion_detail.status
     *
     * @param status the value for integretion_detail.status
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column integretion_detail.type
     *
     * @return the value of integretion_detail.type
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public Byte getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column integretion_detail.type
     *
     * @param type the value for integretion_detail.type
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column integretion_detail.create_date
     *
     * @return the value of integretion_detail.create_date
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column integretion_detail.create_date
     *
     * @param createDate the value for integretion_detail.create_date
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table integretion_detail
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", integretionId=").append(integretionId);
        sb.append(", userId=").append(userId);
        sb.append(", amount=").append(amount);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", createDate=").append(createDate);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table integretion_detail
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
        IntegretionDetail other = (IntegretionDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getIntegretionId() == null ? other.getIntegretionId() == null : this.getIntegretionId().equals(other.getIntegretionId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table integretion_detail
     *
     * @mbg.generated Sat Apr 28 16:57:49 CST 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIntegretionId() == null) ? 0 : getIntegretionId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table integretion_detail
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id"),
        integretionId("integretion_id"),
        userId("user_id"),
        amount("amount"),
        status("status"),
        type("type"),
        createDate("create_date");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table integretion_detail
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table integretion_detail
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table integretion_detail
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table integretion_detail
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column) {
            this.column = column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table integretion_detail
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.column + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table integretion_detail
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.column + " ASC";
        }
    }
}