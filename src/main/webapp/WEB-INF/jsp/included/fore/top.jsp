<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="top" >
    <a href="${pageContext.request.contextPath}">
        <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
        OneMall首页
    </a>

    <span>喵，欢迎来OneMall</span>

    <c:choose >
        <c:when test="${null!= sessionScope.user}">
            <span>
            <a href="login">${sessionScope.user.name}</a>
            <a href="forelogout" >退出</a>
            </span>
        </c:when>
        <c:otherwise>
            <span>
        <a href="login">请登录</a>
        <a href="register">免费注册</a>
            </span>
        </c:otherwise>
    </c:choose>
    <span class="pull-right">
            <a href="bought">我的订单</a>
            <a href="cart">
            <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
                <c:choose >
                    <c:when test="${sessionScope.cartTotalItemNumber} != null &&${sessionScope.cartTotalItemNumber}!=0">
                        购物车<strong>${sessionScope.cartTotalItemNumber}</strong>件</a>
                    </c:when>
                    <c:otherwise>
                        购物车<strong>空</strong></a>
                    </c:otherwise>
                </c:choose>
    </span>
</nav>