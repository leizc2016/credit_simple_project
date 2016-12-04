<%@tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<div class="col-sm-6">
		<div class="dataTables_info" id="dataTables-example_info"
			role="status" aria-live="polite"><!-- Showing 1 to 10 of 57 entries --></div>
	</div>
	<div class="col-sm-6">
		<div class="dataTables_paginate paging_simple_numbers"
			id="dataTables-example_paginate">
			<ul class="pagination">
				<li class="paginate_button previous"
					aria-controls="dataTables-example" tabindex="0" id="prePageli">
					<a href="?pn=${commonPaginator.prePage}&ps=${param.ps}&${searchParams}" id="prePageId">上一页</a>
				</li>		
				<c:forEach var="i" begin="1" end="${commonPaginator.totalPages}" step="1">
				    <li class="paginate_button ${i eq commonPaginator.page ?'active':''}" 
				    aria-controls="dataTables-example" tabindex="0"><a href="?pn=${i}&ps=${param.ps}&${searchParams}">${i}</a></li>
				</c:forEach>
				<li class="paginate_button next"
					aria-controls="dataTables-example" id="nexePageli"
					tabindex="0" id="dataTables-example_next">
					<a href="?pn=${commonPaginator.nextPage}&ps=${param.ps}&${searchParams}" id="nexePageId">下一页</a>
				</li>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		if("${commonPaginator.hasPrePage}" == 'false'){
			$("#prePageId").attr('href','#');
			$("#prePageli").addClass("disabled");
		}else if("${commonPaginator.hasPrePage}" == 'true'){
			$("#prePageId").attr('href','?pn=${commonPaginator.prePage}&ps=${param.ps}&${searchParams}');
			$("#prePageli").removeClass("disabled");
		}
		
		if("${commonPaginator.hasNextPage}" == 'false'){
			$("#nexePageId").attr('href', '#');
			$("#nexePageli").addClass("disabled");
		}else if("${commonPaginator.hasNextPage}" == 'true'){
			$("#nexePageId").attr('href', '?pn=${commonPaginator.nextPage}&ps=${param.ps}&${searchParams}');
			$("#nexePageli").removeClass("disabled");
		}
	});
</script>

