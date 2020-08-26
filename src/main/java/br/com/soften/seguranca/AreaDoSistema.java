/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.seguranca;

/**
 * Representa todos menus/submenus do sistema que abrem alguma tela. Caso mudem
 * de nome, a atualização é feita aqui
 *
 * @author Renan
 */
public enum AreaDoSistema {

    CADASTROS_USUARIOS("Cadastro Usuarios");

    private String label;

    private AreaDoSistema(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

}
