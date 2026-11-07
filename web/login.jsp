<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Đăng nhập - Student Management System</title>
<style>
  :root{
    --bg:#0b0b14; --card:#14141f; --line:#26263a; --text:#f2f2f8; --muted:#8b8ba7;
    --accent:#3b82f6; --accent2:#8b5cf6; --danger:#f87171;
  }
  *{box-sizing:border-box;margin:0;padding:0}
  body{
    min-height:100vh; display:grid; place-items:center; padding:24px;
    font-family:"Segoe UI",Roboto,Helvetica,Arial,sans-serif; color:var(--text);
    background:
      radial-gradient(600px 400px at 15% 10%, rgba(59,130,246,.25), transparent 60%),
      radial-gradient(500px 400px at 90% 90%, rgba(139,92,246,.22), transparent 60%),
      var(--bg);
  }
  .card{
    width:100%; max-width:420px; padding:40px 36px; border-radius:20px;
    background:rgba(20,20,31,.85); border:1px solid var(--line);
    box-shadow:0 30px 80px rgba(0,0,0,.5); backdrop-filter:blur(8px);
  }
  .logo{
    width:56px;height:56px;border-radius:16px;margin:0 auto 18px;display:grid;place-items:center;
    background:linear-gradient(135deg,var(--accent),var(--accent2)); font-size:26px;
    box-shadow:0 10px 30px rgba(59,130,246,.45);
  }
  h1{font-size:22px;text-align:center;font-weight:700}
  .sub{text-align:center;color:var(--muted);font-size:13px;margin:6px 0 24px}
  .alert-error{
    background:rgba(248,113,113,.1); border:1px solid rgba(248,113,113,.3);
    color:var(--danger); padding:10px 14px; border-radius:10px; font-size:13px;
    margin-bottom:18px; text-align:center;
  }
  label{display:block;font-size:13px;color:var(--muted);margin-bottom:6px}
  .field{margin-bottom:18px}
  .input-wrap{position:relative}
  input{
    width:100%; padding:13px 14px; border-radius:12px; font-size:14px; color:var(--text);
    background:#0e0e18; border:1px solid var(--line); outline:none; transition:.2s;
  }
  input:focus{border-color:var(--accent); box-shadow:0 0 0 3px rgba(59,130,246,.2)}
  .btn-submit{
    width:100%; padding:14px; border-radius:12px; border:none; margin-top:8px;
    background:linear-gradient(135deg,var(--accent),var(--accent2)); color:#fff;
    font-weight:600; font-size:15px; cursor:pointer; transition:.2s;
  }
  .btn-submit:hover{opacity:.92; transform:translateY(-1px)}
  .hints{
    margin-top:20px; padding:12px; border-radius:10px; background:rgba(255,255,255,.03);
    border:1px dashed var(--line); font-size:12px; color:var(--muted); text-align:center;
  }
</style>
</head>
<body>
<div class="card">
  <div class="logo">🎓</div>
  <h1>Student Management</h1>
  <p class="sub">Hệ thống Quản lý Sinh viên & Đăng ký môn (CSD201)</p>

  <% if (request.getAttribute("ERROR") != null) { %>
    <div class="alert-error"><%= request.getAttribute("ERROR") %></div>
  <% } %>

  <form action="mainController" method="POST">
    <input type="hidden" name="action" value="Login"/>
    <div class="field">
      <label for="userID">Mã đăng nhập (UserID / MSSV)</label>
      <input type="text" id="userID" name="userID" required placeholder="admin hoặc SE205280" value="admin"/>
    </div>

    <div class="field">
      <label for="password">Mật khẩu</label>
      <input type="password" id="password" name="password" required placeholder="Mật khẩu" value="123"/>
    </div>

    <button type="submit" class="btn-submit">Đăng nhập</button>
  </form>

  <div class="hints">
    💡 Tài khoản thử nghiệm: <b>admin / 123</b> hoặc <b>SE205280 / 123</b>
  </div>
</div>
</body>
</html>
