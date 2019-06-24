<%@ page  contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8"%>
<div class="pageDiv" >
    <nav>
        <ul class="pagination">
            <li :class="{ disabled: pagination.isFirstPage }">
                <a  href="#nowhere" @click="jump('first')">«</a>
            </li>
            <li :class="{ disabled: !pagination.hasPreviousPage }">
                <a  href="#nowhere" @click="jump('pre')">‹</a>
            </li>

            <li  v-for="i in pagination.navigatepageNums">
                <a href="#nowhere" @click="jumpByNumber(i)" >
                    {{i}}
                </a>
            </li>

            <li :class="{ disabled: !pagination.hasNextPage }">
                <a  href="#nowhere" @click="jump('next')">›</a>
            </li>
            <li :class="{ disabled: pagination.isLastPage }">
                <a  href="#nowhere" @click="jump('last')"> »</a>
            </li>
        </ul>
    </nav>
</div>