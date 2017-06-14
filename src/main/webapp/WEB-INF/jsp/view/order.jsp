<spring:message code="title.order" var="orderTitle"/>
<template:main htmlTitle="${orderTitle}">
    <jsp:body>
        <div class="container content-wrap">
        <div class="row">
            <div class="col-xs-10 col-xs-offset-1">
                <h2><spring:message code="order.head"/></h2>

                <div>
                    <p><spring:message code="order.label.orderId"/>: ${orderId}</p>
                    <p><spring:message code="order.label.status"/>: ${status}</p>
                    <p><spring:message code="order.label.total"/>: ${totalPrice}</p>
                    <p><spring:message code="order.label.name"/>: ${customerName}</p>
                    <p><spring:message code="order.label.email"/>: ${customerEmail}</p>
                    <p><spring:message code="order.label.phone"/>: ${customerPhone}</p>
                    <p><spring:message code="order.label.placement.time"/>: ${placementTime}</p>
                    <p><spring:message code="order.label.comment"/>: ${comment}</p>
                </div>
            </div>
        </div>
    </jsp:body>
</template:main>