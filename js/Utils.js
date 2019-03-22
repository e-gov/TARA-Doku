
function init() {

  // Hide all submenus
  // $('.alammenyy').hide();

  // Bind toggling handler to submenu switches
  /* $('.lyliti').click(function() {
    var i = $(this).attr('id');
    $('#alammenyy' + i).toggle();
  }); */

  // Kui valgusrežiim on valitud, siis rakenda see
  var valgus = localStorage.getItem('valgus');
  if (valgus) {
    document.documentElement.setAttribute('data-theme', valgus);
    // Sea ka lüliti positsioon
    if (valgus == 'dark') {
      $('#kuu').addClass('hidden');
      $('#paike').removeClass('hidden');
    }
  }

  // Sea valgustusrežiimi lüliti
  $('#paike').click(() => {
    $('#paike').addClass('hidden');
    $('#kuu').removeClass('hidden');
    document.documentElement.setAttribute('data-theme', 'light');
    // Salvesta valik lokaalmällu
    localStorage.setItem('valgus', 'light');
  }); 
  $('#kuu').click(() => {
    $('#kuu').addClass('hidden');
    $('#paike').removeClass('hidden');
    document.documentElement.setAttribute('data-theme', 'dark');
    localStorage.setItem('valgus', 'dark');
  }); 
}