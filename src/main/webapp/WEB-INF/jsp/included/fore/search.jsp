<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<div>
    <a href="${pageContext.request.contextPath}">
        <img id="logo" src="img/site/logo.gif" class="logo">
    </a>
    <form action="search" method="get" >
        <div class="searchDiv">
            <input name="keyword" type="text" placeholder="时尚男鞋  太阳镜 ">
            <button  type="submit" class="searchButton">搜索</button>
            <div class="searchBelow">
        </div>
        </div>
    </form>
</div>
