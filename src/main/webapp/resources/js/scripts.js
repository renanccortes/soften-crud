/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



function mascaraCpfCnpj(input) {

    //verifica se a tecla precionada nao e um backspace e delete
    var i = input.value.length; //aqui pega o tamanho do input

    if (i <= 14) {
        if (i === 3 || i === 7) //aqui faz a divisoes colocando um ponto no terceiro e setimo indice
            input.value = input.value + ".";
        else if (i === 11) //aqui faz a divisao colocando o tracinho no decimo primeiro indice
            input.value = input.value + "-";
    } else if (i === 15) { //mask: '00.000.000/0000-00'  
        var value = input.value.replace(".", "").replace(".", "").replace("-", "");
     
        value = inserirTexto(value, ".", 2); 
        value = inserirTexto(value, ".", 6);  
        value = inserirTexto(value, "/", 10); 
        value = inserirTexto(value, "-", 15); 
        input.value = value;
    } else { 
    }
 

}

function inserirTexto(TEXTO_ORIGEM, TEXTO_INSERIDO, INDICE) {
    return(""
            + TEXTO_ORIGEM.substring(0, INDICE)
            + TEXTO_INSERIDO
            + TEXTO_ORIGEM.substring(INDICE)
            );
}

 

function mascaraMoeda(objTextBox, SeparadorMilesimo, SeparadorDecimal, e) {
    var sep = 0;
    var key = '';
    var i = j = 0;
    var len = len2 = 0;
    var strCheck = '0123456789';
    var aux = aux2 = '';
    var whichCode = (window.Event) ? e.which : e.keyCode;

    if (whichCode == 13)
        return true;
    key = String.fromCharCode(whichCode); // Valor para o código da Chave
    if (strCheck.indexOf(key) == -1)
        return false; // Chave inválida
    len = objTextBox.value.length;
    for (i = 0; i < len; i++)
        if ((objTextBox.value.charAt(i) != '0') && (objTextBox.value.charAt(i) != SeparadorDecimal))
            break;
    aux = '';
    for (; i < len; i++)
        if (strCheck.indexOf(objTextBox.value.charAt(i)) != -1)
            aux += objTextBox.value.charAt(i);
    aux += key;
    len = aux.length;
    if (len == 0)
        objTextBox.value = '';
    if (len == 1)
        objTextBox.value = '0' + SeparadorDecimal + '0' + aux;
    if (len == 2)
        objTextBox.value = '0' + SeparadorDecimal + aux;
    if (len > 2) {
        aux2 = '';
        for (j = 0, i = len - 3; i >= 0; i--) {
            if (j == 3) {
                aux2 += SeparadorMilesimo;
                j = 0;
            }
            aux2 += aux.charAt(i);
            j++;
        }
        objTextBox.value = '';
        len2 = aux2.length;
        for (i = len2 - 1; i >= 0; i--)
            objTextBox.value += aux2.charAt(i);
        objTextBox.value += SeparadorDecimal + aux.substr(len - 2, len);
    }
    return false;
}