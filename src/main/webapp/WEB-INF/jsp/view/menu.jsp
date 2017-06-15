<spring:message code="title.menu" var="menuTitle"/>
<template:main htmlTitle="${menuTitle}">
    <jsp:attribute name="activeLink">menu</jsp:attribute>

    <h1 align="center"> AFTER PUSH DEPLOY </h1>

    <jsp:body>
        <div class="container content-wrap">
            <div class="row">
                <c:forEach items="${animals}" var="animal">
                    <div class="col-sm-6 col-md-4 col-lg-3">
                        <div class="thumbnail">
                            <img src="<c:url value="${animal.imageUrl}"/>"
                                 class="img-responsive img-circle center-block" alt="${animal.name}">
                            <div class="caption">
                                <h3>${animal.name}</h3>
                                <p class="small">${animal.description}</p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="container-fluid prices">
                <div class="row">
                    <c:forEach items="${tickets}" var="ticket">
                        <div class="col-lg-3">
                            <p>
                                <strong>${ticket.type}: ${ticket.price}&#x20b4;</strong>
                            </p>
                            <div class="submit bottom">
                                <p>
                                    <button type="button" class="btn btn-success btn-block buy-button"
                                            onclick="addToCart( '${ticket.domainId}', '1')">
                                        <span class="glyphicon glyphicon-shopping-cart"></span>
                                        <spring:message code="menu.button.buy"/>
                                    </button>
                                </p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <c:if test="${not empty aviaries}">
                <div class="caption text-center">
                    <h3><spring:message code="menu.schedule.for.contact.aviaries"/> </h3>
                </div>
                <table class="table table-inverse">
                    <thead>
                    <tr>
                        <th><spring:message code="menu.aviary"/></th>
                        <th><spring:message code="menu.time"/></th>
                        <th><spring:message code="menu.animals"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${aviaries}" var="aviary">
                        <tr>
                            <td>${aviary.number}</td>
                            <td>
                                <c:forEach items="${aviary.schedule}" var="schedule">
                                    <p>${schedule}</p>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach items="${aviary.animals}" var="animal">
                                    <p>${animal.name} (${animal.type})</p>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

        </div>
    </jsp:body>
</template:main>