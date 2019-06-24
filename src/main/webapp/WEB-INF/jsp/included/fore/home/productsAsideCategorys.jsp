<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div >
    <div  :cid="c.id" class="productsAsideCategorys" v-for="c in categories">
        <div class="row show1" v-for="ps in c.productsByRow">
            <a :href="'product?pid='+p.id" v-for="p in ps" v-if="p.subTitle.length!=0">
                {{p.subTitle | subTitleFilter}}
            </a>
            <div class="seperator"></div>
        </div>
    </div>
</div>
