<spring:message code="title.error" var="errorPageTitle"/>
<template:main htmlTitle="${errorPageTitle}">

    <jsp:body>

        <div class="container content-wrap">

        <div class="row">

            <div class="col-xs-10 col-xs-offset-1">

                <h2><spring:message code="errorpage.head"/>: <spring:message code="${errorTitle}"/></h2>

                <div>
                    <p><spring:message code="${errorMessage}" arguments="${errorArguments}"/></p>
                </div>

            </div>

        </div>

    </jsp:body>

</template:main>