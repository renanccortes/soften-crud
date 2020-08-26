/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.services;

import br.com.soften.exception.DaoException;
import java.util.List;
import java.util.Map;

/**
 * TODAS CLASSES QUE IMPLEMENTAR ESSA INTERFACE DEVERÁ TER ESTAS FUNÇÕES
 * PADRÕES.
 *
 * @author Renan
 * @param <Entidade>
 */
public interface ServiceGenerico<Entidade> {

    /**
     * SALVAR GENERICO, SERVE TANTO PARA SALVAR E ATUALIZAR.
     *
     * @param entidade
     * @param update
     * @return
     * @throws br.com.soften.exception.DaoException
     */
    public Entidade onSalvar(Entidade entidade, boolean update) throws DaoException;

    /**
     * SALVAR GENERICO, SALVA UMA LISTA DEPOIS EFETUA O COMMIT
     *
     * @param entidades
     * @throws br.com.soften.exception.DaoException
     */
    public void onSalvar(List<Entidade> entidades) throws DaoException;

    /**
     * EXCLUIR GENÉRICO, RECEBE A ENTIDADE, É NECESSÁRIO TER O ID DA ENTIDADE
     *
     * @param entidade
     * @param id
     * @throws br.com.soften.exception.DaoException
     */
    public void onExcluir(Entidade entidade, Long id) throws DaoException;

    /**
     * RETORNA AS ENTIDADES CONFORME A PÁGINA E OS FILTROS PASSADOS PELA
     * DATATABLE DO PRIMEFACES É USADO NA LazyTableGenerico
     *
     * @param inicio
     * @param fim
     * @param filtrosOperadorAND
     * @param filtrosOperadorOR
     * @return
     */
    List<Entidade> buscaPaginada(int inicio, int fim, Map<String, Object> filtrosOperadorAND,
            Map<String, Object[]> filtrosOperadorOR);

    /**
     * RETORNA AS ENTIDADES CONFORME A PÁGINA E OS FILTROS PASSADOS PELA
     * DATATABLE DO PRIMEFACES É USADO NA LazyTableGenerico
     *
     * @param inicio
     * @param fim
     * @param filtrosOperadorAND
     * @param filtrosOperadorOR
     * @param sortField
     * @param ascendingOrder
     * @return
     */
    List<Entidade> buscaPaginada(int inicio, int fim, Map<String, Object> filtrosOperadorAND,
            Map<String, Object[]> filtrosOperadorOR,
            String sortField,
            boolean ascendingOrder);

    /**
     * RETORNA A QUANTIDADE DE REGISTROS CONFORME OS FILTROS PASSADOS
     *
     * @param filtrosOperadorAND
     * @param filtrosOperadorOR
     * @return
     */
    int countLinhas(Map<String, Object> filtrosOperadorAND, Map<String, Object[]> filtrosOperadorOR);

    /**
     * RETORNA UM ÚNICO ELEMENTO DE UMA BUSCA
     *
     * @param filtrosOperadorAND
     * @param filtrosOperadorOR
     * @return
     */
    Entidade buscaUnicaUnicaComParametros(Map<String, Object> filtrosOperadorAND, Map<String, Object[]> filtrosOperadorOR);

    /**
     * BUSCA REGISTROS SEM PAGINAÇÃO
     *
     * @param filtrosOperadorAND
     * @param filtrosOperadorOR
     * @return
     */
    List<Entidade> buscaComParametros(Map<String, Object> filtrosOperadorAND, Map<String, Object[]> filtrosOperadorOR);

    /**
     * BUSCA TODOS ELEMENTOS DA TABELA
     *
     * @return
     */
    public List<Entidade> findAll();

    /**
     * BUSCA UM REGISTRO PELO ID
     *
     * @param id
     * @return
     */
    public Entidade findOne(Object id);

    public List<?> loadLazyRelationship(Object myEntity, String relacionamento);

}
