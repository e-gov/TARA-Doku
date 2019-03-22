
function init() {

  // Hide all submenus
  // $('.alammenyy').hide();

  // Bind toggling handler to submenu switches
  /* $('.lyliti').click(function() {
    var i = $(this).attr('id');
    $('#alammenyy' + i).toggle();
  }); */

  // Sea valgustusrežiimi lüliti
  $('#paike').click(() => {
    $('#paike').addClass('hidden');
    $('#kuu').removeClass('hidden');
    document.documentElement.setAttribute('data-theme', 'light');
  }); 
  $('#kuu').click(() => {
    $('#kuu').addClass('hidden');
    $('#paike').removeClass('hidden');
    document.documentElement.setAttribute('data-theme', 'dark');
  }); 
}