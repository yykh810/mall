<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div class>
    <script>
        $(function(){
            var keyword = getUrlParms("keyword");
            var data4Vue = {
                uri:'foresearch',
                products:[]
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
                        var url =  this.uri+"?keyword="+keyword;
                        axios.post(url).then(function(response) {
                            vue.products = response.data.list;
                            vue.$nextTick(function(){
                                linkDefaultActions();
                            })
                        });
                    }
                }
            });
        })
    </script>
    <div id="searchResult">
        <div class="searchResultDiv">
            <%@ include file="productsBySearch.jsp" %>
        </div>
    </div>
</div>