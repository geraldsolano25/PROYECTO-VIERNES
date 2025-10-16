
function submitContact(e){
  e.preventDefault();
  const name = document.getElementById('name').value;
  const email = document.getElementById('email').value;
  const message = document.getElementById('message').value;
  alert('Gracias ' + name + '! Tu mensaje ha sido enviado (simulado).');
  document.getElementById('contactForm').reset();
  return false;
}
