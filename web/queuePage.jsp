<%@page import="dto.RegistrationRequest"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Hàng đợi đăng ký môn (FIFO) - Student Management</title>
<style>
  :root{
    --bg:#0b0b14; --card:#14141f; --card2:#1a1a29; --line:#26263a; --text:#f2f2f8; --muted:#8b8ba7;
    --accent:#3b82f6; --accent2:#8b5cf6; --success:#10b981; --warn:#f59e0b;
  }
  *{box-sizing:border-box;margin:0;padding:0}
  body{
    min-height:100vh; padding-bottom:60px;
    font-family:"Segoe UI",Roboto,Helvetica,Arial,sans-serif; color:var(--text);
    background:radial-gradient(700px 400px at 90% -5%, rgba(139,92,246,.18), transparent 60%), var(--bg);
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

  main{max-width:900px;margin:0 auto;padding:32px 20px}
  h1{font-size:26px;margin-bottom:6px}
  .sub{color:var(--muted);font-size:14px;margin-bottom:20px}

  .alert{padding:12px 16px;border-radius:12px;background:rgba(16,185,129,.15);border:1px solid var(--success);color:#6ee7b7;font-size:14px;margin-bottom:20px}

  .action-bar{display:flex;justify-content:space-between;align-items:center;margin-bottom:20px;flex-wrap:wrap;gap:12px}
  .btn{padding:12px 20px;border-radius:12px;border:none;cursor:pointer;font-size:14px;font-weight:600;display:inline-flex;align-items:center;gap:8px;transition:.2s}
  .btn-approve{background:linear-gradient(135deg,var(--accent),var(--accent2));color:#fff}
  .btn-approve:hover{opacity:.92;transform:translateY(-1px)}

  .table-wrap{background:var(--card);border:1px solid var(--line);border-radius:16px;overflow:hidden}
  table{width:100%;border-collapse:collapse;font-size:14px}
  th{text-align:left;padding:14px 16px;background:rgba(255,255,255,.02);color:var(--muted);font-weight:600;border-bottom:1px solid var(--line)}
  td{padding:14px 16px;border-bottom:1px solid rgba(255,255,255,.05);vertical-align:middle}
  tr:last-child td{border-bottom:none}
  tr:hover td{background:rgba(255,255,255,.02)}

  .badge-pos{width:26px;height:26px;border-radius:50%;display:grid;place-items:center;background:var(--card2);border:1px solid var(--line);font-size:12px;font-weight:700}
  .badge-first{background:rgba(59,130,246,.2);border-color:var(--accent);color:#60a5fa}
  .badge-code{font-family:monospace;font-weight:700;color:#c084fc;background:rgba(192,132,252,.1);padding:3px 8px;border-radius:6px}
  .badge-status{display:inline-block;padding:3px 8px;border-radius:6px;font-size:12px;font-weight:600;background:rgba(245,158,11,.15);color:var(--warn)}
</style>
</head>
<body>
<%
  String username = (String) session.getAttribute("USERFULLNAME");
  if (username == null) username = "Academic Advisor";
  String message = (String) request.getAttribute("MESSAGE");
  List<RegistrationRequest> list = (List<RegistrationRequest>) session.getAttribute("LISTQUEUE");
  Integer queueCount = (Integer) request.getAttribute("QUEUE_COUNT");
  if (queueCount == null && list != null) queueCount = list.size();
%>
<header>
  <div class="brand">
    <div class="logo">🎓</div>
    <span>Student Management System</span>
  </div>
  <nav>
    <a href="showStudentController">Danh sách sinh viên</a>
    <a href="showQueueController" class="active">Hàng đợi đăng ký môn (FIFO)</a>
  </nav>
  <div class="user">
    <span>Xin chào, <b><%= username %></b></span>
    <a href="logoutController" class="btn-logout">Đăng xuất</a>
  </div>
</header>

<main>
  <h1>Hàng đợi đăng ký môn học (FIFO Queue)</h1>
  <p class="sub">Quản lý và phê duyệt các yêu cầu đăng ký môn học theo thứ tự thời gian (First-In First-Out)</p>

  <% if (message != null) { %>
    <div class="alert">✅ <%= message %></div>
  <% } %>

  <div class="action-bar">
    <div>
      Số lượng yêu cầu đang chờ: <b><%= (queueCount != null ? queueCount : 0) %></b>
    </div>
    <form action="showQueueController" method="POST">
      <input type="hidden" name="action" value="approve"/>
      <button type="submit" class="btn btn-approve" <%= (queueCount == null || queueCount == 0) ? "disabled style='opacity:0.5;cursor:not-allowed'" : "" %>>
        ⚡ Duyệt yêu cầu tiếp theo (FIFO Dequeue)
      </button>
    </form>
  </div>

  <div class="table-wrap">
    <table>
      <thead>
        <tr>
          <th style="width:60px;">Vị trí</th>
          <th>Mã sinh viên (MSSV)</th>
          <th>Mã môn học</th>
          <th>Thời gian gửi</th>
          <th>Trạng thái hàng đợi</th>
        </tr>
      </thead>
      <tbody>
        <% if (list != null && !list.isEmpty()) {
             int pos = 1;
             for (RegistrationRequest r : list) { %>
          <tr>
            <td>
              <div class="badge-pos <%= (pos == 1 ? "badge-first" : "") %>">
                <%= pos %>
              </div>
            </td>
            <td><b><%= r.getStudentId() %></b></td>
            <td><span class="badge-code"><%= r.getCourseCode() %></span></td>
            <td style="color:var(--muted);"><%= r.getRequestTime() %></td>
            <td>
              <% if (pos == 1) { %>
                <span class="badge-status" style="background:rgba(59,130,246,.15);color:#60a5fa">▶ Kế tiếp được duyệt</span>
              <% } else { %>
                <span class="badge-status">Đang chờ xử lý</span>
              <% } %>
            </td>
          </tr>
        <%     pos++;
             }
           } else { %>
          <tr>
            <td colspan="5" style="text-align:center;padding:36px;color:var(--muted)">
              Hàng đợi hiện đang rỗng! Chưa có yêu cầu đăng ký nào cần duyệt.
            </td>
          </tr>
        <% } %>
      </tbody>
    </table>
  </div>
</main>
</body>
</html>
