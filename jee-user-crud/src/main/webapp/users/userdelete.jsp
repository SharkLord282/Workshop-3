<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/header.jsp" %>

<div class="container-fluid">


    <!-- Page Heading -->

    <div class="d-sm-flex align-items-center justify-content-between mb-4">

        <h1 class="h3 mb-0 text-gray-800">UsersCRUD</h1>

        <a href="<c:url value="/user/list"/>" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">

            <i class="fas fa-download fa-sm text-white-50"></i> Lista użytkowników</a>

    </div>

    <div class="card shadow mb-4">

        <div class="card-header py-3">

            <h6 class="m-0 font-weight-bold text-primary">Usuwanie użytkownika</h6>

        </div>

        <div class="card-body">

            <form action="/user/userDelete" method="post">

                <table class="table">
                    <tr>
                        <th>Nazwa użytkownika</th>
                        <th>Email</th>
                        <th>Akcja</th>
                    </tr>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.userName}</td>
                            <td>${user.email}</td>
                            <td>
                                <button type="submit" value="${user.id}" name="id" class="btn btn-primary">Usuń</button>
                            </td>
                        </tr>
                    </c:forEach>

                </table>


            </form>


        </div>

    </div>

</div>

<%@ include file="/footer.jsp" %>