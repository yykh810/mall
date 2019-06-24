<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class >
    <a href="${pageContext.request.contextPath}">
        <img id="logo" src="img/site/logo.gif" class="logo">
    </a>
    <form action="search" method="get" >
        <div class="searchDiv">
            <input name="keyword" type="text" placeholder="时尚男鞋  太阳镜 ">
            <button  type="submit" class="searchButton">搜索</button>
            <div class="searchBelow">
                <c:forEach items="${application.categories_below_search}" var="c" varStatus="st">
                    <c:if test="${status.index>=5 && status.index<=8}">
                    <span>
                        <a href="'category?cid='+${c.id}">${c.name}</a>
                         <c:if test="${status.index!=8}">
                             <span>|</span>
                         </c:if>
                    </span>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </form>
</div>
