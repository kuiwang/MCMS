<!DOCTYPE html>
<html lang="zh">
 <head>
<#include "/manager/include/macro.ftl"/>
<#include "/manager/include/meta.ftl"/>
</head>
<body>	
<@ms.content>
	<@ms.contentBody>
		<@ms.contentNav title="栏目管理" >
			<@ms.savebutton  id="saveUpdate" value=""/>
			<@ms.button class="btn btn-default returnList" value="返回列表" />
		</@ms.contentNav >
		<@ms.contentPanel>
			<@ms.form name="columnForm" isvalidation=true  action="" method="post" >
				<#if column.categoryId!=0>
					<@ms.hidden name="categoryId" value="${column.categoryId?c?default(0)}" />
				</#if>
				<@ms.text name="categoryTitle" style="width: 15%;" label="${Session.model_title_session?default('栏目')}名称" title="${Session.model_title_session?default('栏目')}名称" placeholder="${Session.model_title_session?default('栏目')}名称" value="${column.categoryTitle?default('')}" id="" labelStyle="width:15%"/>
				<div class="form-group" style="overflow: inherit; min-height: 39px;">
	        		<label class="col-md-3  col-xs-3" style="width:15%;">所属栏目</label>
	        		<div class="col-md-3  col-xs-3" style="padding: 0;">
						<@ms.treeInput treeId="inputTree"  json="${listColumn?default('')}"  jsonId="categoryId" jsonPid="categoryCategoryId" jsonName="categoryTitle" inputName="categoryCategoryId" inputValue="${column.categoryCategoryId?c?default(0)}" addNodesName="顶级栏目管理"  buttonText="${columnSuper.categoryTitle?default('顶级栏目管理')}" clickZtreeId="clickZtreeId(event,treeId,treeNode);"  expandAll="true" showIcon="true"/>
	 				</div>
          		</div>
          		<@ms.text name="categorySort"   style="width: 10%;"  label="自定义顺序" title="自定义顺序" size="5"  placeholder="请输入文章顺序" value="${column.categorySort?c?default(0)}" labelStyle="width:15%" validation={"data-bv-between":"true","data-bv-between-message":"自定义顺序必须大于0","data-bv-between-min":"0", "data-bv-between-max":"99999999","data-bv-notempty-message":"自定义顺序不能为空"}/>
          		<@ms.textarea name="columnKeyword" label="${Session.model_title_session?default('栏目')}关键字" wrap="Soft" rows="4" placeholder="${Session.model_title_session?default('栏目')}关键字，有助于搜索"   value="${column.columnKeyword?default('')}" labelStyle="width:15%"/>
          		<@ms.textarea name="columnDescrip" label="${Session.model_title_session?default('栏目')}描述" wrap="Soft" rows="4" placeholder="${Session.model_title_session?default('栏目')}描述，对${Session.model_title_session?default('栏目')}关键字的扩展"   value="${column.columnDescrip?default('')}" labelStyle="width:15%"/>
				<#assign columnTypes=[{"id":"1","name":"列表"},{"id":"2","name":"封面"}]>
				<@ms.radio name="columnType" label="${Session.model_title_session?default('栏目')}属性"  list=columnTypes listKey="id" listValue="name" value="${column.columnType?c?default(1)}" labelStyle="width:15%"/>
				<#if listCm?has_content>
						<@ms.select name="columnContentModelId" style="width: 15%;"  list="listCm"  listKey="cmId" listValue="cmTipsName"  default="普通文章"  label="${Session.model_title_session?default('栏目')}内容模型"  value="" labelStyle="width:15%"/>
					<#else>
						<input name="columnContentModelId" value="${column.columnContentModelId?c?default(0)}" type="hidden"/>
				</#if>
				<#assign columnMOdelUrls=[{"id":"0","name":"暂无文件"}]>
				<@ms.select name="columnListUrl" style="width: 15%;" id="columnListUrlModel"  list="columnMOdelUrls"  listKey="id" listValue="name" label="列表模版"  value="${column.columnListUrl?default('')}"  labelStyle="width:15%"/>
				<@ms.select name="columnUrl" style="width: 15%;"id="columnUrlModel" default="暂无文件"  list="columnMOdelUrls"  listKey="id" listValue="name" label="内容模版"  value="${column.columnUrl?default('')}"  labelStyle="width:15%"/>
			</@ms.form>
		</@ms.contentPanel>
	</@ms.contentBody>
</@ms.content>    

</body>


<script>
$(function(){
	<#if column.categoryId==0>
	$("#saveUpdate").text("保存");
	<#else>
	var columnContentModelId= "${column.columnContentModelId?default('')}"
	$("select[name=columnContentModelId]").find("option[value="+columnContentModelId+"]").attr("selected","selected");
	$("#saveUpdate").text("更新");
	</#if>
	<#if column.columnType == 0> 
		  $("input:radio[name='columnType']:first").attr("checked",true);
	</#if>
	var columnListUrlSes ="${column.columnListUrl?default('')}";
	var columnUrlSes = "${column.columnUrl?default('')}";
	//页面加载列表模板和内容模板
	$.ajax({
	   type: "post",
	   dataType: "json",
	   url:  "${base}/manager/cms/templet/queryTempletFileForColumn.do",
	   success: function(msg){
	   		$("#columnListUrlModel").html("");
	     	$("#columnUrlModel").html("");
	     	if(msg.length != 0){
	   			for(var i=0; i<msg.length; i++){
	   				if(msg[i] == columnListUrlSes) {
	   					$("#columnListUrlModel").append("<option selected>"+msg[i]+"</option>")
	   				}else{
	   					$("#columnListUrlModel").append("<option>"+msg[i]+"</option>")
	   				}
		   			if( msg[i] == columnUrlSes){
		   				$("#columnUrlModel").append("<option selected>"+msg[i]+"</option>")
		   			}else{
		   				$("#columnUrlModel").append("<option>"+msg[i]+"</option>")
		   			}
		   		}
	   		}else{
	   			$("#columnListUrlModel").append("<option>暂无文件</option>");
			   	$("#columnUrlModel").append("<option>暂无文件</option>");
	   		}
	   		<#if column.columnType ==2 >
				$("#columnListUrlModel").parents(".form-group").hide();
				$("#columnListUrlModel").css("disabled",true);
				$("#columnUrlModel").parent().prev().text("封面模板:");
			</#if>
	   }
	});	
	//切换栏目属性
	$("input[name='columnType']").click(function(){
		if($(this).val()== 2){
			$("#columnListUrlModel").parents(".form-group").hide();
			$("#columnListUrlModel").css("disabled",true);
			$("#columnUrlModel").parent().prev().text("封面模板:");
		}else if($(this).val()== 1){
			$("#columnListUrlModel").parents(".form-group").show();
			$("#columnListUrlModel").css("disabled",false);
			$("#columnUrlModel").parent().prev().text("内容模板:");
		}
	});
	
	//返回栏目列表
	$(".returnList").click(function(){
		location.href = base+"/manager/cms/column/list.do";
	});
	
	
	//栏目保存提交事件
	$("#saveUpdate").click(function(){
		if($("#columnListUrlModel").find("option:selected").text()=="暂无文件"){
			$("#columnListUrlModel").find("option:selected").text("");
		}
		if($("#columnUrlModel").find("option:selected").text()=="暂无文件"){
			$("#columnUrlModel").find("option:selected").text("");
		}
		//如果选择的是普通文章，则
		if($("select[name=columnContentModelId]").find("option:selected").val()==""){
			$("select[name=columnContentModelId]").find("option:selected").val(0)
		}
		var formdata = $("#columnForm").serialize();
		var URL = "";
		<#if column.categoryId==0>
		URL =base+"/manager/cms/column/save.do";
		<#else>
		URL = base+"/manager/cms/column/update.do";
		</#if>
		if(isNaN($("input[name=categorySort]").val())){
			alert("自定义排序必须是数字");
			$("input[name=categorySort]").val(0);
			$("#saveUpdate").css("disabled",false);
			return;
		}
		$(this).css("disabled",true);
		$.ajax({
		   	type: "post",
		   	url: URL,
		   	data: formdata,
		   	dataType:"json",
		   	success: function(msg){
		    	if (msg.result) {
	     			<#if column.categoryId==0>
	     			alert("保存成功");
	     			<#else>
	     			alert("更新成功");
	     			</#if>
	    			location.href=base+"/manager/cms/column/list.do?categoryId="+msg.resultData;
	    		}else{
	    			<#if column.categoryId==0>
	     			alert("保存失败");
	     			<#else>
	     			alert("更新失败");
	     			</#if>
	    			$("#saveUpdate").css("disabled",false);
	    		}
		   	}
		});
	});
	
});

//选择栏目后查询自定义模型
function clickZtreeId(event,treeId,treeNode){
	//栏目不能选择自己及其子栏目为父栏目的事件
	<#if column.categoryId gt 0 >
		var booleanClick=true;
		var nodeParam = zTreeObjinputTree.getNodesByParam("categoryId", "${column.categoryId?c?default(0)}", null);
		var nodes = zTreeObjinputTree.getNodesByParam("categoryId", treeNode.categoryId, nodeParam[0]);
		if(nodes.length>0 || treeNode.categoryId == nodeParam[0].categoryId){
			booleanClick=false;
			alert("不能选择该栏目作为父栏目")
		}
		return booleanClick;
	</#if>
} 


</script>
</html>
