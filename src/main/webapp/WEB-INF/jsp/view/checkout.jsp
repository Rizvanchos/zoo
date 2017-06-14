<spring:message code="title.checkout" var="checkoutTitle"/>
<template:main htmlTitle="${checkoutTitle}">

    <jsp:attribute name="headContent">
          <script src="<c:url value="/resources/js/order_details.js" />"></script>
    </jsp:attribute>

    <jsp:body>
        <div class="container content-wrap">
            <div class="row">
                <div class="col-xs-10 col-xs-offset-1">
                    <h2><spring:message code="checkout.head.content"/></h2>

                    <c:choose>
                        <c:when test="${empty cart.items}">
                            <p class="text-muted"><spring:message code="cart.noitems"/></p>
                        </c:when>
                        <c:otherwise>

                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th class="table-column-productname">
                                        <spring:message code="cart.column.ticket"/>
                                    </th>
                                    <th><spring:message code="cart.column.price"/></th>
                                    <th><spring:message code="cart.column.quantity"/></th>
                                    <th><spring:message code="cart.column.cost"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${cart.items}" var="item">
                                    <tr>
                                        <td class="table-column-productname">${item.key.type}</td>
                                        <td>${item.key.price} &#x20b4;</td>
                                        <td>${item.value}</td>
                                        <td>${item.key.price * item.value} &#x20b4;</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <p><spring:message code="cart.total"/>: <strong>${cart.totalCost}</strong> &#x20b4;</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <c:if test="${not empty cart.items}">
                <div class="row">
                    <div class="col-xs-10 col-xs-offset-1">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" value="false" data-toggle="collapse" data-target="#orderDetails">
                                <spring:message code="checkout.confirmcontent"/>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row collapse" id="orderDetails">
                    <div class="col-xs-10 col-xs-offset-1">
                        <h2><spring:message code="checkout.head.details"/></h2>

                        <div>
                            <form id="orderDetailsForm" class="form-horizontal" method="post">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="contactName">
                                        <spring:message code="form.checkout.label.name"/>
                                    </label>
                                    <div class="col-sm-6">
                                        <div class="input-group">
                                            <span class="input-group-addon"><i
                                                    class="glyphicon glyphicon-user"></i></span>
                                            <input class="form-control" type="text" id="contactName" name="contactName"
                                                   placeholder="<spring:message code="form.checkout.hint.name" />"/>
                                        </div>
                                        <span class="help-block"><spring:message code="form.example"/>: John Smith</span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="contactPhone">
                                        <spring:message code="form.checkout.label.phone"/></label>
                                    <div class="col-sm-6">
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span>
                                            <input type="text" class="form-control" id="contactPhone" name="contactPhone"
                                                   pattern="\(\d{3}\)\d{3}-\d{2}-\d{2}" placeholder="<spring:message code="form.checkout.hint.phone" />"/>
                                        </div>
                                        <span class="help-block">
                                            <spring:message code="form.example"/>: <spring:message code="form.example.phone.number"/>
                                        </span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="contactEmail">
                                        <spring:message code="form.checkout.label.email"/></label>
                                    <div class="col-sm-6">
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                                            <input class="form-control" type="email" id="contactEmail" name="contactEmail"
                                                   placeholder="<spring:message code="form.checkout.hint.email" />"/>
                                        </div>
                                        <span class="help-block"><spring:message code="form.example"/>: john.smith@gmail.com</span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="comment"><spring:message code="form.checkout.label.comment" /></label>
                                    <div class="col-sm-6">
                                        <textarea class="form-control" id="comment" name="comment"></textarea>
                                        <span class="help-block">(<spring:message code="form.optional" />)</span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-sm-6 col-sm-offset-2">
                                        <button type="submit" class="btn btn-success">
                                            <spring:message code="form.checkout.button.submit"/>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>

    </jsp:body>
</template:main>