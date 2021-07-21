<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="[=mapperPkg].[=mapperName]">
  <resultMap id="BaseResultMap" type="[=modelPkg].[=modelName]">
    <id column="t_id" jdbcType="BIGINT" property="id" />
    <#list fields as field>
    <result column="[=field.columnNameWithPrefix]" jdbcType="[=field.jdbcType]" property="[=field.lcColumnName]" />
    </#list>
  </resultMap>
  <sql id="Base_Column_List">
    t.id as t_id,
    <#list fields as field>
    t.[=field.columnName] as [=field.columnNameWithPrefix]<#sep>,</#sep>
    </#list>
  </sql>
  <select id="selectByPrimaryKey" parameterType="java.lang.Long" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from [=tableName] t
    where t.id = #{id,jdbcType=BIGINT}
  </select>
  <delete id="deleteByPrimaryKey" parameterType="java.lang.Long">
    delete from [=tableName]
    where id = #{id,jdbcType=BIGINT}
  </delete>
  <insert id="insert" parameterType="[=modelPkg].[=modelName]">
    insert into [=tableName] (
      id,
      <#list fields as field>
      [=field.columnName]<#sep>,</#sep>
      </#list>
    )
    values (
      #{id,jdbcType=BIGINT},
      <#list fields as field>
      #{[=field.lcColumnName],jdbcType=[=field.jdbcType]}<#sep>,</#sep>
      </#list>
    )
  </insert>
  <update id="updateByPrimaryKeySelective" parameterType="[=modelPkg].[=modelName]">
    update [=tableName]
    <set>
      <#list fields as field>
      <if test="[=field.lcColumnName] != null">
        [=field.columnName] = #{[=field.lcColumnName],jdbcType=[=field.jdbcType]},
      </if>
      </#list>
    </set>
    where id = #{id,jdbcType=BIGINT}
  </update>
  <select id="selectPaged" parameterType="java.util.HashMap" resultMap="BaseResultMap">
    <!--
    <if test="userName != null">
      <bind name="userNamePattern" value="'%'+userName+'%'"/>
      and user_name like #{userNamePattern}
    </if>
    -->
    select
    <include refid="Base_Column_List" />
    from [=tableName] t
    <where>
      <#list fields as field>
      <if test="[=field.lcColumnName] != null">
        and [=field.columnName] = #{[=field.lcColumnName],jdbcType=[=field.jdbcType]}
      </if>
      </#list>
    </where>
    <if test="orderByClause != null">
      order by ${orderByClause}
    </if>
  </select>
  <#if hasUniqueKey >
  <select id="selectByUniqueKey" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from [=tableName] t
    <where>
      <#list ukFields as field>
      and t.[=field.columnName] = #{[=field.lcColumnName],jdbcType=[=field.jdbcType]}
      </#list>
      <if test="notThisId != null">
        and t.id != #{notThisId,jdbcType=BIGINT}
      </if>
    </where>
  </select>
  </#if>
</mapper>