<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div >
    <div class="categoryMenu">
        <div :id="c.id" class="eachCategory" v-for="c in categories">
            <span class="glyphicon glyphicon-link"></span>
            <a :href="'category?cid='+c.category.id">
                {{c.category.name}}
            </a>
        </div>
    </div>
</div>