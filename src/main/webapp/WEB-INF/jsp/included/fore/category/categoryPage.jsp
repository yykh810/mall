<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div >
    <script>
        $(function(){
            var cid = getUrlParms("cid");
            var sort = getUrlParms("sort");
            var data4Vue = {
                uri:'forecategory',
                c:'',
                sort:''
            };
            //ViewModel
            var vue = new Vue({
                el: '#workingArea',
                data: data4Vue,
                mounted:function(){ //mounted　表示这个 Vue 对象加载成功了
                    this.load();
                },
                methods: {
                    load:function(){
                        this.sort = sort;
                        var url =  this.uri+"/"+cid+"?sort="+sort;
                        axios.get(url).then(function(response) {
                            vue.c = response.data;
                            vue.$nextTick(function(){
                                linkDefaultActions();
                            })
                        });

                    }
                }
            });

        })
    </script>

    <title>模仿天猫官网-{{c.name}}</title>
    <div id="category">
        <div class="categoryPageDiv">
            <img v-if="c.id!=null" :src="'img/category/'+c.id+'.jpg'">
            <%@ include file="../category/sortBar.jsp" %>
            <%@ include file="../category/productsByCategory.jsp" %>
        </div>
    </div>
</div>