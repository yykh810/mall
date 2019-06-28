<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div>
    <a href="${pageContext.request.contextPath}">
        <img id="simpleLogo" class="simpleLogo" src="img/site/simpleLogo.png">
    </a>
    <form action="search" method="get" >
        <div class="simpleSearchDiv pull-right">
            <input type="text" placeholder="平衡车 原汁机"  name="keyword">
            <button class="searchButton" type="submit">搜天猫</button>
            <div class="searchBelow">
            </div>
        </div>
    </form>
    <div style="clear:both"></div>
</div>