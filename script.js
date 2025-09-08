async function askBot() {
  const query = document.getElementById("query").value;

  const res = await fetch("/chat", {
    method: "POST",
    headers: { "Content-Type": "text/plain" },
    body: query
  });

  const text = await res.text();
  document.getElementById("response").innerText = text;
}
