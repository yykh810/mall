<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div >
    <img src="img/site/catear.png" id="catear" class="catear"/>

    <div class="categoryWithCarousel">

        <div class="headbar show1">
            <div class="head ">

                <span style="margin-left:10px" class="glyphicon glyphicon-th-list"></span>
                <span style="margin-left:10px" >商品分类</span>

            </div>

            <div class="rightMenu">
                <span><a href=""><img src="img/site/chaoshi.png"/></a></span>
                <span><a href=""><img src="img/site/guoji.png"/></a></span>

                <span v-for="c,index in categories" v-if='index<=3'>
                        <a :href="'category?cid='+c.category.id">{{c.category.name}}</a>
                    </span>
            </div>

        </div>
        <div style="position: relative">
            <%@ include file="categoryMenu.jsp" %>
        </div>
        <div style="position: relative;left: 0;top: 0;">
            <%@ include file="productsAsideCategorys.jsp" %>
        </div>
            <%@ include file="carousel.jsp" %>
        <div class="carouselBackgroundDiv">
        </div>

    </div>
</div>