<%@page import="dto.UserDTO"%>
<%@page import="dto.Student"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Danh sách sinh viên - Student Management</title>
<style>
  :root{
    --bg:#0b0b14; --card:#14141f; --card2:#1a1a29; --line:#26263a; --text:#f2f2f8; --muted:#8b8ba7;
    --accent:#3b82f6; --accent2:#8b5cf6; --success:#10b981; --warn:#f59e0b;
  }
  *{box-sizing:border-box;margin:0;padding:0}
  body{
    min-height:100vh; padding-bottom:60px;
    font-family:"Segoe UI",Roboto,Helvetica,Arial,sans-serif; color:var(--text);
    background:radial-gradient(700px 400px at 10% -5%, rgba(59,130,246,.2), transparent 60%), var(--bg);
  }
  header{
    position:sticky;top:0;z-index:10; display:flex;align-items:center;justify-content:space-between;gap:16px;
    padding:14px 32px; background:rgba(11,11,20,.85); backdrop-filter:blur(10px); border-bottom:1px solid var(--line);
  }
  .brand{display:flex;align-items:center;gap:10px;font-weight:700;font-size:17px}
  .brand .logo{width:34px;height:34px;border-radius:10px;display:grid;place-items:center;
    background:linear-gradient(135deg,var(--accent),var(--accent2));font-size:18px}
  nav{display:flex;gap:6px}
  nav a{padding:8px 16px;border-radius:999px;color:var(--muted);text-decoration:none;font-size:14px;transition:.2s}
  nav a:hover{color:var(--text)}
  nav a.active{background:var(--card2);color:var(--text);font-weight:600}
  .user{display:flex;align-items:center;gap:12px;font-size:14px;color:var(--muted)}
  .btn-logout{background:none;border:1px solid var(--line);color:var(--text);padding:6px 14px;border-radius:999px;cursor:pointer;font-size:13px;text-decoration:none;transition:.2s}
  .btn-logout:hover{border-color:var(--accent)}

  main{max-width:1050px;margin:0 auto;padding:32px 20px}
  h1{font-size:26px;margin-bottom:6px}
  .sub{color:var(--muted);font-size:14px;margin-bottom:20px}

  .alert{padding:12px 16px;border-radius:12px;background:rgba(59,130,246,.15);border:1px solid var(--accent);color:#93c5fd;font-size:14px;margin-bottom:20px}

  .toolbar{display:flex;gap:12px;margin-bottom:20px;flex-wrap:wrap;align-items:center}
  .search{flex:1;min-width:240px;position:relative}
  .search input{
    width:100%;padding:12px 16px;border-radius:12px;font-size:14px;color:var(--text);
    background:var(--card);border:1px solid var(--line);outline:none;transition:.2s;
  }
  .search input:focus{border-color:var(--accent);box-shadow:0 0 0 3px rgba(59,130,246,.2)}
  .btn{padding:12px 18px;border-radius:12px;border:none;cursor:pointer;font-size:14px;font-weight:600;text-decoration:none;display:inline-flex;align-items:center;gap:6px;transition:.2s}
  .btn-primary{background:linear-gradient(135deg,var(--accent),var(--accent2));color:#fff}
  .btn-secondary{background:var(--card);border:1px solid var(--line);color:var(--text)}
  .btn-secondary:hover{border-color:var(--accent)}
  .count{color:var(--muted);font-size:13px;margin-bottom:14px}

  .table-wrap{background:var(--card);border:1px solid var(--line);border-radius:16px;overflow:hidden}
  table{width:100%;border-collapse:collapse;font-size:14px}
  th{text-align:left;padding:14px 16px;background:rgba(255,255,255,.02);color:var(--muted);font-weight:600;border-bottom:1px solid var(--line)}
  td{padding:14px 16px;border-bottom:1px solid rgba(255,255,255,.05);vertical-align:middle}
  tr:last-child td{border-bottom:none}
  tr:hover td{background:rgba(255,255,255,.02)}

  .badge-id{font-family:monospace;font-weight:700;color:#60a5fa}
  .badge-gpa{display:inline-block;padding:3px 8px;border-radius:6px;font-weight:700;font-size:12px;background:rgba(16,185,129,.15);color:var(--success)}
  .courses-pill{display:inline-block;padding:2px 8px;border-radius:999px;font-size:12px;background:var(--card2);border:1px solid var(--line);color:var(--text)}

  .form-inline{display:flex;gap:6px;align-items:center}
  .select-course{background:#0e0e18;border:1px solid var(--line);color:var(--text);padding:6px 10px;border-radius:8px;font-size:13px;outline:none}
  .btn-enroll{background:linear-gradient(135deg,var(--accent),var(--accent2));color:#fff;border:none;padding:6px 12px;border-radius:8px;font-size:12px;font-weight:600;cursor:pointer}
</style>
</head>
<body>
<%
  String username = (String) session.getAttribute("USERFULLNAME");
  if (username == null) username = "Academic Advisor";
  String message = (String) request.getAttribute("MESSAGE");
  List<Student> list = (List<Student>) request.getAttribute("STUDENTLIST");
  String keyword = (String) request.getAttribute("KEYWORD");
  Integer total = (Integer) request.getAttribute("TOTALSTUDENT");
%>
<header>
  <div class="brand">
    <div class="logo">🎓</div>
    <span>Student Management System</span>
  </div>
  <nav>
    <a href="showStudentController" class="active">Danh sách sinh viên</a>
    <a href="showQueueController">Hàng đợi đăng ký môn (FIFO)</a>
  </nav>
  <div class="user">
    <span>Xin chào, <b><%= username %></b></span>
    <a href="logoutController" class="btn-logout">Đăng xuất</a>
  </div>
</header>

<main>
  <h1>Danh sách Sinh viên (Singly Linked List & BST Index)</h1>
  <p class="sub">Quản lý hồ sơ học tập và đăng ký môn học (CSD201 - Topic 2)</p>

  <% if (message != null) { %>
    <div class="alert">📌 <%= message %></div>
  <% } %>

  <div class="toolbar">
    <form action="showStudentController" method="GET" class="search" style="display:flex;gap:8px;">
      <input type="text" name="KEYWORD" placeholder="Tìm kiếm theo MSSV, Tên hoặc Chuyên ngành..." value="<%= (keyword != null ? keyword : "") %>"/>
      <button type="submit" class="btn btn-primary">Tìm</button>
    </form>
    <a href="showStudentController?sortBy=gpa" class="btn btn-secondary">⭐ Sắp xếp GPA giảm dần</a>
    <a href="showStudentController" class="btn btn-secondary">🔄 Tải lại</a>
  </div>

  <div class="count">
    Tổng số sinh viên hiển thị: <b><%= (list != null ? list.size() : 0) %></b> / <%= (total != null ? total : 0) %>
  </div>

  <div class="table-wrap">
    <table>
      <thead>
        <tr>
          <th>MSSV</th>
          <th>Họ và Tên</th>
          <th>Chuyên ngành</th>
          <th>GPA</th>
          <th>Môn đã đăng ký</th>
          <th>Thao tác đăng ký môn</th>
        </tr>
      </thead>
      <tbody>
        <% if (list != null && !list.isEmpty()) {
             for (Student s : list) { %>
          <tr>
            <td><span class="badge-id"><%= s.getId() %></span></td>
            <td><b><%= s.getName() %></b></td>
            <td><%= s.getMajor() %></td>
            <td><span class="badge-gpa"><%= String.format("%.2f", s.getGpa()) %></span></td>
            <td><span class="courses-pill"><%= s.getCourses() %></span></td>
            <td>
              <form action="showStudentController" method="POST" class="form-inline">
                <input type="hidden" name="action" value="enroll"/>
                <input type="hidden" name="studentId" value="<%= s.getId() %>"/>
                <select name="courseCode" class="select-course">
                  <option value="CSD201">CSD201</option>
                  <option value="MAS291">MAS291</option>
                  <option value="PRO192">PRO192</option>
                  <option value="PRF192">PRF192</option>
                </select>
                <button type="submit" class="btn-enroll">+ Đăng ký</button>
              </form>
            </td>
          </tr>
        <%   }
           } else { %>
          <tr>
            <td colspan="6" style="text-align:center;padding:32px;color:var(--muted)">Không tìm thấy sinh viên nào</td>
          </tr>
        <% } %>
      </tbody>
    </table>
  </div>
</main>
</body>
</html>
