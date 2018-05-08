package com.training.core.repo.po;

import java.util.ArrayList;
import java.util.List;

public class OrgFinanceVenuesExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrgFinanceVenuesExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andVenueNameIsNull() {
            addCriterion("venueName is null");
            return (Criteria) this;
        }

        public Criteria andVenueNameIsNotNull() {
            addCriterion("venueName is not null");
            return (Criteria) this;
        }

        public Criteria andVenueNameEqualTo(String value) {
            addCriterion("venueName =", value, "venueName");
            return (Criteria) this;
        }

        public Criteria andVenueNameNotEqualTo(String value) {
            addCriterion("venueName <>", value, "venueName");
            return (Criteria) this;
        }

        public Criteria andVenueNameGreaterThan(String value) {
            addCriterion("venueName >", value, "venueName");
            return (Criteria) this;
        }

        public Criteria andVenueNameGreaterThanOrEqualTo(String value) {
            addCriterion("venueName >=", value, "venueName");
            return (Criteria) this;
        }

        public Criteria andVenueNameLessThan(String value) {
            addCriterion("venueName <", value, "venueName");
            return (Criteria) this;
        }

        public Criteria andVenueNameLessThanOrEqualTo(String value) {
            addCriterion("venueName <=", value, "venueName");
            return (Criteria) this;
        }

        public Criteria andVenueNameLike(String value) {
            addCriterion("venueName like", value, "venueName");
            return (Criteria) this;
        }

        public Criteria andVenueNameNotLike(String value) {
            addCriterion("venueName not like", value, "venueName");
            return (Criteria) this;
        }

        public Criteria andVenueNameIn(List<String> values) {
            addCriterion("venueName in", values, "venueName");
            return (Criteria) this;
        }

        public Criteria andVenueNameNotIn(List<String> values) {
            addCriterion("venueName not in", values, "venueName");
            return (Criteria) this;
        }

        public Criteria andVenueNameBetween(String value1, String value2) {
            addCriterion("venueName between", value1, value2, "venueName");
            return (Criteria) this;
        }

        public Criteria andVenueNameNotBetween(String value1, String value2) {
            addCriterion("venueName not between", value1, value2, "venueName");
            return (Criteria) this;
        }

        public Criteria andVenueNoteIsNull() {
            addCriterion("venueNote is null");
            return (Criteria) this;
        }

        public Criteria andVenueNoteIsNotNull() {
            addCriterion("venueNote is not null");
            return (Criteria) this;
        }

        public Criteria andVenueNoteEqualTo(String value) {
            addCriterion("venueNote =", value, "venueNote");
            return (Criteria) this;
        }

        public Criteria andVenueNoteNotEqualTo(String value) {
            addCriterion("venueNote <>", value, "venueNote");
            return (Criteria) this;
        }

        public Criteria andVenueNoteGreaterThan(String value) {
            addCriterion("venueNote >", value, "venueNote");
            return (Criteria) this;
        }

        public Criteria andVenueNoteGreaterThanOrEqualTo(String value) {
            addCriterion("venueNote >=", value, "venueNote");
            return (Criteria) this;
        }

        public Criteria andVenueNoteLessThan(String value) {
            addCriterion("venueNote <", value, "venueNote");
            return (Criteria) this;
        }

        public Criteria andVenueNoteLessThanOrEqualTo(String value) {
            addCriterion("venueNote <=", value, "venueNote");
            return (Criteria) this;
        }

        public Criteria andVenueNoteLike(String value) {
            addCriterion("venueNote like", value, "venueNote");
            return (Criteria) this;
        }

        public Criteria andVenueNoteNotLike(String value) {
            addCriterion("venueNote not like", value, "venueNote");
            return (Criteria) this;
        }

        public Criteria andVenueNoteIn(List<String> values) {
            addCriterion("venueNote in", values, "venueNote");
            return (Criteria) this;
        }

        public Criteria andVenueNoteNotIn(List<String> values) {
            addCriterion("venueNote not in", values, "venueNote");
            return (Criteria) this;
        }

        public Criteria andVenueNoteBetween(String value1, String value2) {
            addCriterion("venueNote between", value1, value2, "venueNote");
            return (Criteria) this;
        }

        public Criteria andVenueNoteNotBetween(String value1, String value2) {
            addCriterion("venueNote not between", value1, value2, "venueNote");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("createTime =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("createTime <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("createTime >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("createTime >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("createTime <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("createTime <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("createTime like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("createTime not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("createTime in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("createTime not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("createTime between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("createTime not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("updateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("updateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(String value) {
            addCriterion("updateTime =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(String value) {
            addCriterion("updateTime <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(String value) {
            addCriterion("updateTime >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("updateTime >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(String value) {
            addCriterion("updateTime <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(String value) {
            addCriterion("updateTime <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLike(String value) {
            addCriterion("updateTime like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotLike(String value) {
            addCriterion("updateTime not like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<String> values) {
            addCriterion("updateTime in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<String> values) {
            addCriterion("updateTime not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(String value1, String value2) {
            addCriterion("updateTime between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(String value1, String value2) {
            addCriterion("updateTime not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}