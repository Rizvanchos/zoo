<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="headContent" fragment="true" required="false" %>
<%@ attribute name="activeLink" type="java.lang.String" rtexprvalue="true" required="false" %>
<%@ include file="/WEB-INF/jsp/base.jspf" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><spring:message code="title.company"/> :: <c:out value="${fn:trim(htmlTitle)}"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/external/bootstrap.min.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/external/bootstrapValidator.min.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/external/languages.min.css" />"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha/css/bootstrap.min.css">

    <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"/>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="<c:url value="/resources/js/external/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/external/ztoast.min.js" />"></script>
    <script src="<c:url value="/resources/js/external/bootstrapValidator.min.js" />"></script>

    <script src="<c:url value="/resources/js/set_locale.js" />"></script>
    <script src="<c:url value="/resources/js/cart.js" />"></script>
    <script src="<c:url value="/resources/js/track_order.js" />"></script>

    <jsp:invoke fragment="headContent"/>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#navbar-collapse-1">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand pull-left" href="/">
                <img id="navbar-logo" alt="Brand" src="<c:url value="/resources/images/zoo-logo.jpg"/>">
            </a>
        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse-1">

            <ul class="nav navbar-nav">
                <li <c:if test="${activeLink.equals('menu')}">
                    class="active"
                </c:if>>
                    <a href="/menu/"><i class="glyphicon glyphicon-list-alt"></i>
                        <spring:message code="navigation.menu"/>
                    </a>
                </li>
                <li class="dropdown">
                    <c:set var="languageCode" value="${pageContext.response.locale.language}"/>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"><span
                            class="lang-sm lang-lbl-full" lang="${languageCode}"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li>
                            <button type="button" class="btn btn-link" onclick="setLocale('en');"><span
                                    class="lang-sm lang-lbl-full" lang="en"></span></button>
                        </li>
                        <li>
                            <button type="button" class="btn btn-link" onclick="setLocale('ru');"><span
                                    class="lang-sm lang-lbl-full" lang="ru"></span></button>
                        </li>
                        <li>
                            <button type="button" class="btn btn-link" onclick="setLocale('uk');"><span
                                    class="lang-sm lang-lbl-full" lang="uk"></span></button>
                        </li>
                    </ul>
                </li>
            </ul>

            <div class="navbar-right">
                <button type="submit" class="btn btn-default navbar-btn" data-toggle="modal"
                        data-target="#trackOrderModal">
                    <i class="glyphicon glyphicon-search"></i> <spring:message code="navigation.trackorder"/>
                </button>
                <button type="button" class="btn btn-default navbar-btn" id="btnCart" data-toggle="modal"
                        data-target="#addToCartModal">
                    <span class="glyphicon glyphicon-shopping-cart"></span> <spring:message code="navigation.cart"/>
                    <span class="badge" id="badgeCart" hidden></span>
                </button>
            </div>

        </div>
    </div>
</nav>

<jsp:doBody/>

<div class="modal fade" id="addToCartModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">
                    <span class="glyphicon glyphicon-shopping-cart"></span>
                    <spring:message code="title.cart"/>
                </h4>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success pull-left" id="btnCheckout" onclick="checkoutCart();">
                    <span class="glyphicon glyphicon-usd"></span>
                    <spring:message code="form.cart.button.checkout"/>
                </button>
                <button type="button" class="btn btn-link" id="btnClear" onclick="clearCart()">
                    <spring:message code="form.cart.button.clear"/>
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <spring:message code="form.button.cancel"/>
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="trackOrderModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">
                    <span class="glyphicon glyphicon-search"></span> <spring:message code="title.trackorder"/>
                </h4>
            </div>
            <div class="modal-body">
                <form id="trackOrderForm" class="form-horizontal" action="/trackorder/" method="post">
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="inputTrackOrderId">
                            <spring:message code="form.track.label.orderId"/>
                        </label>
                        <div class="col-sm-9">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-tag"></i></span>
                                <input class="form-control" type="text" id="inputTrackOrderId" name="inputTrackOrderId"
                                       placeholder="<spring:message code="form.track.hint.orderId" />"/>
                            </div>
                            <span class="help-block"><spring:message code="form.example"/>: e2ecad90-51ae-4e9a-a6bf-09e31a962dc4</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="inputTrackOrderEmail">
                            <spring:message code="form.track.label.email"/>
                        </label>
                        <div class="col-sm-9">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                                <input class="form-control" type="text" id="inputTrackOrderEmail"
                                       name="inputTrackOrderEmail"
                                       placeholder="<spring:message code="form.track.hint.email" />"/>
                            </div>
                            <span class="help-block"><spring:message code="form.example"/>: john.smith@gmail.com</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-2">
                            <button type="submit" class="btn btn-success">
                                <span class="glyphicon glyphicon-search"></span>
                                <spring:message code="form.track.button.track"/>
                            </button>
                            <button type="button" class="btn btn-default pull-right" data-dismiss="modal">
                                <span class="glyphicon glyphicon-remove"></span>
                                <spring:message code="form.button.cancel"/>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
