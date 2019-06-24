<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<head>
    <%@ include file="../included/adminHeader.jsp" %>
</head>
<body>
<div><%@ include file="../included/adminNavigator.jsp" %></div>
<script>
    $(function(){

        var data4Vue = {
            uri:'users',
            beans: [],
            pagination:{}
        };

        //ViewModel
        var vue = new Vue({
            el: '#workingArea',
            data: data4Vue,
            mounted:function(){ //mounted　表示这个 Vue 对象加载成功了
                this.list(0);
            },
            methods: {
                list:function(start){
                    var url =  this.uri+ "?start="+start;
                    axios.get(url).then(function(response) {
                        vue.pagination = response.data;
                        vue.beans = response.data.list   ;
                    });
                },
                jump: function(page){
                    jump(page,vue); //定义在adminHeader.html 中
                },
                jumpByNumber: function(start){
                    jumpByNumber(start,vue);
                }
            }
        });
    });

</script>

<div id="workingArea" >
    <h1 class="label label-info" >用户管理</h1>
    <br>
    <br>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
            <tr class="success">
                <th>ID</th>
                <th>用户名称</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="bean in beans ">
                <td>{{bean.id}}</td>
                <td>
                    {{bean.name}}
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <%@ include file="../included/adminPage.jsp"%>
</div>
<%@ include file="../included/adminFooter.jsp" %>

</body>
</html>