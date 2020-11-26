/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.farmacia.servlet;

import br.com.farmacia.DAO.ClienteDAO;
import br.com.farmacia.DAO.ItemVendaDAO;
import br.com.farmacia.DAO.ProdutoDAO;
import br.com.farmacia.DAO.VendaDAO;
import br.com.farmacia.Model.ItemVenda;
import br.com.farmacia.Model.Produto;
import br.com.farmacia.Model.Venda;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

/**
 *
 * @author felipe
 */
@WebServlet(name = "VendaServlet", urlPatterns = {"/VendaServlet"})
public class VendaServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;
    private static String LIST_CLIENTE_PRODUTO = "/venda.jsp";
    private static String SUCESSO = "/sucesso.jsp";
    private static String ERROR = "/erro.jsp";
    private ClienteDAO daoCliente;
    private ProdutoDAO daoProduto;
    private ItemVendaDAO daoItem;
    private VendaDAO daoVenda;
    private Venda venda;
     ArrayList<ItemVenda> cart;

    public VendaServlet() {
        super();
        daoCliente = new ClienteDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        try {
            if (action.equalsIgnoreCase("listaClienteProduto")) {
                forward = LIST_CLIENTE_PRODUTO;
                request.setAttribute("clientes", daoCliente.getClientes());
                request.setAttribute("produtos", daoProduto.getProdutos());
            }
        } catch (Exception e) {
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "/venda.jsp";
        String action = request.getParameter("action");

        try {
             if(action.equalsIgnoreCase("iniciarVenda")){
                 Venda venda = new Venda();
                 VendaDAO.adicionaVenda(venda);
                 request.setAttribute("venda", venda);
                  cart = new ArrayList<>();
                  
             }
             else if(action.equalsIgnoreCase("addProduto")){
               daoItem = new ItemVendaDAO();
               daoVenda = new VendaDAO();
               int idProduto = Integer.parseInt(request.getParameter("idProduto"));
               int qtd = Integer.parseInt(request.getParameter("qtdProduto"));
               Produto produto = ProdutoDAO.getProduto(idProduto);
               
               ItemVenda item = new ItemVenda();
               item.setProduto(produto);
               item.setVenda(venda);
               item.setQtd(qtd);
//               item.getProduto().setIdProduto(10);
               
               cart.add(item);
               
                
               
//               daoItem.addItemVenda(item);
//               
//               List<ItemVenda> itens = daoItem.getItensVenda(venda.getCod_Venda());
//               for(ItemVenda item2 : venda.getItens()){
//                 item2.getProduto().getNomeProd();
//             }
//               String teste = request.getParameter("itemsVenda");
           
               request.setAttribute("itemsVenda", cart);
               
           }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"erro aqui");
        } finally {
             request.setAttribute("clientes", daoCliente.getClientes());
                request.setAttribute("produtos", daoProduto.getProdutos());
            RequestDispatcher view = request.getRequestDispatcher(forward);
//            request.setAttribute("funcionarios", dao.getFuncionarios());
            view.forward(request, response);
        }
    }

}
