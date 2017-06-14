<c:choose>
    <c:when test="${empty items}">
        <p class="text-muted"><spring:message code="cart.noitems"/></p>
    </c:when>
    <c:otherwise>

        <table class="table table-responsive table-striped">
            <thead>
            <tr>
                <th class="table-column-productname"><spring:message code="cart.column.ticket"/></th>
                <th><spring:message code="cart.column.price"/></th>
                <th><spring:message code="cart.column.quantity"/></th>
                <th><spring:message code="cart.column.cost"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${items}" var="item">
                <tr>
                    <td class="table-column-productname">${item.key.type}</td>
                    <td>${item.key.price} &#x20b4;</td>
                    <td>
                        <input type="number" min="1" value="${item.value}" style="width: 50px"
                               onchange="updateCartItem('${item.key.domainId}', this.value);"/>
                    </td>
                    <td>${item.key.price * item.value} &#x20b4;</td>
                    <td>
                        <button type="button" class="btn btn-warning" onclick="removeCartItem('${item.key.domainId}')">
                            <span class="glyphicon glyphicon-remove"></span> <spring:message code="cart.button.remove"/>
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <p><spring:message code="cart.total"/>: <strong>${cart.totalCost}</strong> &#x20b4;</p>

    </c:otherwise>
</c:choose>