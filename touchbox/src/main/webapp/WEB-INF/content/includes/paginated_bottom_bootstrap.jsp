<%@ page language="java" pageEncoding="UTF-8"%>
<TR bgColor="#ffffff">
    <TD colSpan=15>
	<span style="float:left">
	每页 <select name="pagesize" id="pagesize">
            <option value="20" ng-selected="responseData.pagesize == 20">20</option>
            <option value="50" ng-selected="responseData.pagesize == 50">50</option>
            <option value="100" ng-selected="responseData.pagesize == 100">100</option>
        </select> 条
<A ng-click="pageQuery(1).current()" href="javascript:void(0);"> <img src="${ctx }/images/go.png" alt="" width="16" height="16" align="absmiddle" border="0"></A>
	</span>
	<span style="float:right; align:right">
	<span>共{{responseData.total}}条</span>
	<span ng-show="{{responseData.maxpage}}">
	 [{{responseData.page}}/{{responseData.maxpage}}]页 <span ng-show="{{responseData.isFirstPage}}">[首页][上一页]
			<A ng-click="pageQuery(responseData.page).next()" href="javascript:void(0);">[下一页]</A>
			<A ng-click="pageQuery(responseData.maxpage).current()" href="javascript:void(0);">[末页]</A>
		</span><span ng-show="{{responseData.isLastPage}}">
			<A ng-click="pageQuery(1).current()" href="javascript:void(0);">[首页]</A>
			<A ng-click="pageQuery(responseData.page).previous()" href="javascript:void(0);">[上一页]</A>[下一页][末页]
		</span><span ng-show="{{responseData.isMiddlePage}}">
			<A ng-click="pageQuery(1).current()" href="javascript:void(0);">[首页]</A>
			<A ng-click="pageQuery(responseData.page).previous()" href="javascript:void(0);">[上一页]</A>
			<A ng-click="pageQuery(responseData.page).next()" href="javascript:void(0);">[下一页]</A>
			<A ng-click="pageQuery(responseData.maxpage).current()" href="javascript:void(0);">[末页]</A>
		</span>
		页<INPUT value="{{responseData.page}}" size="2" name="page" id="page" />
		<INPUT ng-click="pageQuery(this.form.page.value).current();" value=Go type="button"/>
	</span>
	</span></TD>
</TR>