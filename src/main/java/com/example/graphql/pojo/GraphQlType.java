package com.example.graphql.pojo;

/**
 * @author wh
 * @date 2020/7/9
 * Description:
 */
public enum GraphQlType {
    /**
     * QUERY 查询
     * MUTATION 变更
     */
    QUERY("query"),
    MUTATION("mutation");
    private String value;

    private GraphQlType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
