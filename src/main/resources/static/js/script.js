$(function() {

    const token = "IGQVJXY0gzVXB2Tk8xbjBLZAVpoSHVjUDF2M05idTN4WHVHbjYtVGNSN2huY0VqRVhLLTJDNURvUWE4WEhmVndrLWEzdHJiZAG4zRFBCQ2d1OTJtaXpRVVJTSGlLT0ZAoRnNNbE5zeWxITDVJTTFOb2p2YgZDZD";
    const url = "https://graph.instagram.com/me/media?access_token=" + token + "&fields=media_url,media_type,caption,permalink";

    $.get(url).then(function(response){
        //console.log('retorno: ', response.data)
        let dadosJson = response.data
        let conteudo = '<div class="row" style="padding-left:5px">';

        for (let p=0; p < dadosJson.length; p++){
            let feed = dadosJson[p];
            let titulo = feed.caption !== null ? feed.caption : '';
            let tipo = feed.media_type;
            if(tipo === 'VIDEO'){
                conteudo += '<div class="col-12 col-sm-6 col-md-4 col-lg-4 col-xl-4 col-xxl-4"><video style="width:100%;heigth:90%" controls><source src="'+feed.media_url+'" type="video/mp4"></video></div>';
            }
            else if(tipo === 'IMAGE'){
                conteudo += '<div class="col-12 col-sm-6 col-md-4 col-lg-4 col-xl-4 col-xxl-4"><img style="width:100%;heigth:90%" title="'+titulo+'" alt="'+titulo+'" src="'+feed.media_url+'" onclick="window.open(\''+ feed.permalink +'\');"></div>';
        }
    }
    conteudo += '</div>';
    $('#insta').html(conteudo);
    })
});
