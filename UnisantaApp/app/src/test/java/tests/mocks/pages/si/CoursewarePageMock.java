package tests.mocks.pages.si;

import si.unisanta.tcc.unisantaapp.domain.exceptions.PageRequestFailedException;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.CoursewarePage;

public class CoursewarePageMock extends CoursewarePage{
    public CoursewarePageMock() {
        super("");
    }

    @Override
    public String getHtml() throws PageRequestFailedException {
        return "\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"pt-br\">\n" +
                "<head>\n" +
                "    <meta name=\"robots\" content=\"noindex, nofollow\" />\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <title>Portal do Aluno Unisanta - Material Did&#225;tico</title>\n" +
                "    <link href=\"/Images/favicon.ico\" rel=\"shortcut icon\" type=\"image/x-icon\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width\" />\n" +
                "    <link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">\n" +
                "    <script src=\"/bundles/modernizr?v=\"></script>\n" +
                "\n" +
                "    <script src=\"/bundles/jquery?v=dP2yITnHXE1VENxIjrMA8762C6o0WKzQYtJrA-_gHUs1\"></script>\n" +
                "\n" +
                "    <script src=\"/bundles/fancybox?v=apcQYwjGmAWJ6Wgkj-11HQjmoo_CAXlLANYLvwjKSgs1\"></script>\n" +
                "\n" +
                "    <link href=\"/Content/css?v=5-pw3ZsEeknAkYrDvIz5jvaoeszvacMV6feGS6Q-5k41\" rel=\"stylesheet\"/>\n" +
                "\n" +
                "    \n" +
                "    \n" +
                "    \n" +
                "\n" +
                "    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300,600,700' rel='stylesheet' type='text/css'>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "    <div id=\"divLoading\">\n" +
                "        <img src=\"/Images/loading.gif\" />\n" +
                "    </div>\n" +
                "    <header>\n" +
                "        <div class=\"topo\">\n" +
                "            <a href=\"/Academico\" title=\"Portal do Aluno Unisanta\" >\n" +
                "                <img src=\"/images/logo.png\" alt=\"Portal do Aluno Unisanta\" />\n" +
                "            </a>\n" +
                "\n" +
                "            <nav class=\"menu_rapido\">\n" +
                "                \n" +
                "    \n" +
                "    <a href=\"/Academico/CalendarioLetivo\"><i class=\"icon-calendar\"></i><br> calendário </a>\n" +
                "    <a href=\"/Academico/Horarios\"><i class=\"icon-time\"></i><br> horários </a>       \n" +
                "\n" +
                "    <a href=\"/Academico/MaterialDidatico\"><i class=\"icon-book\"></i> <br> material </a>     \n" +
                "    <a href=\"/Academico/Notas\"><i class=\"icon-certificate\"></i> <br> notas </a>\n" +
                "    <a href=\"/Financeiro/Pagamentos\"><i class=\"icon-barcode\"></i> <br> pagamentos </a>\n" +
                "    <a href=\"/Informativos/FaleConosco\"><i class=\"icon-comments-alt\"></i> <br> fale conosco </a>\n" +
                "    <a href=\"/Acesso/LogOff\" style=\"padding-right: 0\"><i class=\"icon-signout\"></i> <br> sair </a>\n" +
                "\n" +
                "            </nav>\n" +
                "            <br>\n" +
                "        </div>\n" +
                "        <div class=\"topo_mobile\">\n" +
                "            <img src=\"/images/logo_mob.png\" alt=\"Portal do Aluno - Graduação\" class=\"left\" style=\"padding: 0 0 0 30px\">\n" +
                "            <nav class=\"menu_rapido\">\n" +
                "                <a href=\"/Acesso/LogOff\" style=\"padding-right: 0\"><i class=\"icon-signout\"></i></a>\n" +
                "            </nav>\n" +
                "        </div>\n" +
                "        <div class=\"print-header\">116755 - GUILHERME MAGALHAES LATROVA</div>\n" +
                "    </header>\n" +
                "    <div class=\"onde_estou\">\n" +
                "        <p class=\"left\" style=\"font-size: 13px !important\">\n" +
                "            \n" +
                "            SISTEMAS DE INFORMA&#199;&#195;O\n" +
                "            - 2016/1\n" +
                "        </p>\n" +
                "        <p class=\"right\"><strong>Último acesso:</strong> 08/03/2016, às 15:50</p>\n" +
                "    </div>\n" +
                "    <div class=\"content\">\n" +
                "        <aside>\n" +
                "            \n" +
                "<div class=\"dados_aluno\" style=\"text-align: center\">\n" +
                "\n" +
                "    <img src=\"http://portalaluno.unisanta.br/imagens/kioske/sem-foto-portal.png\" alt=\"GUILHERME MAGALHAES LATROVA\" width=\"148\" height=\"148\" class=\"fotoPerfil\" onclick=\"javascript:alert('Para definir sua foto, vincule seu perfil do Facebook através da opção Dados Pessoais do menu.');\">\n" +
                "   \n" +
                "\n" +
                "    <span style=\"font-size: 13px; font-weight:300; letter-spacing:-0.5px; line-height:1.3em\">GUILHERME MAGALHAES LATROVA</span><br>\n" +
                "    <span class=\"raAluno\"> 116755</span>\n" +
                "    \n" +
                "</div>\n" +
                "\n" +
                "            \n" +
                "\n" +
                "<nav class=\"menu\">\n" +
                "    <ul>        \n" +
                "        <li><a href=\"/Academico\">Home</a></li>  \n" +
                "      \n" +
                "        <li><a class=\"aguarde\" href=\"/Contrato\">Rematr&#237;cula</a></li>\n" +
                "        <li><a class=\"aguarde\" href=\"/Academico/Requerimentos\">Requerimentos</a></li> \n" +
                "   \n" +
                "              \n" +
                "        <li><a class=\"aguarde\" href=\"/Academico/MeusAvisos\">Meus Avisos</a></li>\n" +
                "        <li><a class=\"aguarde\" href=\"/Academico/Avisos\">Avisos Acad&#234;micos</a></li>\n" +
                "        <li><a class=\"aguarde\" href=\"/Academico/MaterialDidatico\">Material Did&#225;tico</a></li>\n" +
                "        <li><a class=\"aguarde\" href=\"/Academico/Notas\">Notas</a></li>\n" +
                "        <li><a class=\"aguarde\" href=\"/Academico/Horarios\">Hor&#225;rios</a></li>\n" +
                "        <li><a class=\"aguarde\" href=\"/Academico/DadosPessoais\">Dados Pessoais</a></li>\n" +
                "        <li><a class=\"aguarde\" href=\"/Financeiro/Pagamentos\">Pagamentos</a></li>\n" +
                "        <li><a class=\"aguarde\" href=\"/Academico/CalendarioLetivo\">Calend&#225;rio Letivo</a></li>\n" +
                "        <li><a class=\"aguarde\" href=\"/Academico/DatasDasProvas\">Datas das Provas</a></li>\n" +
                "        <li><a class=\"aguarde\" href=\"/Academico/HorariosDeProvas?aluno=S\">Hor&#225;rios de Provas</a></li>\n" +
                "        <li><a class=\"aguarde\" href=\"/Academico/LocalizaProfessor\">Localizar Professor</a></li>\n" +
                "        <li><a class=\"aguarde\" href=\"/Documentos\">Servi&#231;os</a></li>\n" +
                "    </ul>\n" +
                "</nav>\n" +
                "<br />\n" +
                "\n" +
                "<nav class=\"menu\">\n" +
                "    <ul>\n" +
                "        <li><a href=\"/Convenio\">Conv&#234;nios</a></li>\n" +
                "        <li><a href=\"/Informativos/ManualDoAluno\">Manual do Aluno</a></li>\n" +
                "        <li><a href=\"/Informativos/Normas\">Normas</a></li>  \n" +
                "        <li><a href=\"/Informativos/Atendimento\">Atendimento</a></li> \n" +
                "    </ul>\n" +
                "</nav>\n" +
                "<br />\n" +
                "\n" +
                "<nav class=\"menu_links\">\n" +
                "    <ul>\n" +
                "        <li><a href=\"/Oportunidades\" title=\"Vagas de estágio\">Vagas de Estágio</a></li>\n" +
                "        <li><a href=\"http://unisanta.br/biblioteca/\" target=\"_blank\" title=\"Consulte a Biblioteca da Unisanta\">Consulte a Biblioteca</a> <i class=\"icon-external-link\" style=\"margin: 0 0 0 -25px\"> </i></li>\n" +
                "        <li><a href=\"http://search.ebscohost.com/login.aspx?authtype=ip,uid&user=s4510040&password=password\" target=\"_blank\" title=\"Consulta de Periódicos - EBSCO\">Periódicos - EBSCO</a> <i class=\"icon-external-link\" style=\"margin: 0 0 0 -25px\"> </i></li>\n" +
                "    </ul>\n" +
                "</nav>\n" +
                "<br />\n" +
                "\n" +
                "<div style=\"text-align:center\">\n" +
                "    \n" +
                "</div>\n" +
                "            \n" +
                "        </aside>\n" +
                "        <article>\n" +
                "            \n" +
                "\n" +
                "\n" +
                "<div class=\"boxDuvidas\">\n" +
                "    <a href=\"/Informativos/FaleConosco?TipoContato=1\">D&#250;vidas sobre os avisos ou documentos desta p&#225;gina?</a>\n" +
                "</div>\n" +
                "\n" +
                "<h2>Material Didático</h2>\n" +
                "<br>\n" +
                "\n" +
                "<p style=\"font-size: 14px\"><a href=\"/Academico/PlanodeEnsino\"> - Clique aqui para acessar o plano de ensino do curso</a></p>\n" +
                "\n" +
                "<br>\n" +
                "<form style=\"font-size: 1.2em; font-weight: bold\">\n" +
                "    \n" +
                "<span>Período Letivo: </span> \n" +
                "<select id=\"dlPeriodoLetivo\">\n" +
                "    \n" +
                "            <option selected=\"selected\" value=\"2016-1\">2016/1</option>\n" +
                "            <option value=\"2015-2\">2015/2</option>\n" +
                "            <option value=\"2015-1\">2015/1</option>\n" +
                "            <option value=\"2014-2\">2014/2</option>\n" +
                "            <option value=\"2014-1\">2014/1</option>\n" +
                "            <option value=\"2013-2\">2013/2</option>\n" +
                "            <option value=\"2013-1\">2013/1</option>\n" +
                "</select>\n" +
                "</form>\n" +
                "<br>\n" +
                "<br>\n" +
                "\n" +
                "<div class=\"titulo_matdid\">AVISOS - SECRETARIA / COORDENADOR - 2016/1</div>\n" +
                "   <div class=\"avisos_sec\">\n" +
                "       <p style=\"margin: 5px 0\">\n" +
                "           <strong>Concurso de Bolsas de Inicia&#231;&#227;o Cient&#237;fica</strong><br>\n" +
                "\n" +
                "\n" +
                "\n" +
                "       \n" +
                "       \n" +
                "       \n" +
                "       \n" +
                "       V&#225;lido para alunos do 1. ao 3. ano. Veja o Edital publicado em Arquivos.\n" +
                "Al&#233;m de concorrer no programa de Bolsas, a pesquisa contar&#225; horas de ATIVIDADES COMPLEMENTARES.</p>\n" +
                "   </div>\n" +
                "\n" +
                "<br style=\"clear: both\" />\n" +
                "<br />\n" +
                "<div class=\"titulo_matdid\">DISCIPLINAS - 2016/1</div>\n" +
                "\n" +
                "<div class=\"lista_prof\" style=\"margin: 0\">\n" +
                "<form action=\"/Academico/Disciplina\" id=\"fmMaterialDidatico\" method=\"post\">    <input type=\"hidden\" name=\"CodTur\" id=\"CodTur\" />\n" +
                "    <input type=\"hidden\" name=\"CodMat\" id=\"CodMat\" />\n" +
                "    <input type=\"hidden\" name=\"PeriodoLetivo\" id=\"PeriodoLetivo\" />\n" +
                "    <input type=\"hidden\" name=\"Disciplina\" id=\"Disciplina\" />\n" +
                "    <input type=\"hidden\" name=\"Professor\" id=\"Professor\" />\n" +
                "        <ul id=\"disciplinasMaterialDidatico\" style=\"margin: 5px 0 0 15px\">\n" +
                "        <li>\n" +
                "            <a rel=\"1052N7B|4342|2016/1|AUDITORIA E SEGURAN&#199;A DA INFORMA&#199;&#195;O I|LUIZ ANTONIO FERRARO MATHIAS\" class=\"disciplinasMaterialDidatico\" href=\"javascript:void(0)\" title=\"AUDITORIA E SEGURAN&#199;A DA INFORMA&#199;&#195;O I\">AUDITORIA E SEGURAN&#199;A DA INFORMA&#199;&#195;O I</a>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <a rel=\"1052N7B|5085|2016/1|&#201;TICA, MEIO AMBIENTE E SUSTENTABILIDADE|ALFREDO CORDELLA\" class=\"disciplinasMaterialDidatico\" href=\"javascript:void(0)\" title=\"&#201;TICA, MEIO AMBIENTE E SUSTENTABILIDADE\">&#201;TICA, MEIO AMBIENTE E SUSTENTABILIDADE</a>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <a rel=\"1052N7B|4369|2016/1|GEST&#195;O DA INFORM&#199;&#195;O|ALFREDO CORDELLA\" class=\"disciplinasMaterialDidatico\" href=\"javascript:void(0)\" title=\"GEST&#195;O DA INFORM&#199;&#195;O\">GEST&#195;O DA INFORM&#199;&#195;O</a>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <a rel=\"1052N7B|4343|2016/1|INTERFACE HOMEM M&#193;QUINA|FRANCIELE ALVES SANTOS MEDINA\" class=\"disciplinasMaterialDidatico\" href=\"javascript:void(0)\" title=\"INTERFACE HOMEM M&#193;QUINA\">INTERFACE HOMEM M&#193;QUINA</a>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <a rel=\"1052N7B|2319|2016/1|PESQUISA OPERACIONAL I|JOSE AVELINO DOS SANTOS MOURA\" class=\"disciplinasMaterialDidatico\" href=\"javascript:void(0)\" title=\"PESQUISA OPERACIONAL I\">PESQUISA OPERACIONAL I</a>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <a rel=\"1052N7B|2464|2016/1|PRATICA E GERENCIAMENTO DE PROJETOS I|ANTONIO CARLOS MARQUES DO AMARAL GUERRA\" class=\"disciplinasMaterialDidatico\" href=\"javascript:void(0)\" title=\"PRATICA E GERENCIAMENTO DE PROJETOS I\">PRATICA E GERENCIAMENTO DE PROJETOS I</a>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <a rel=\"1052N7B|4341|2016/1|SISTEMAS COOPERATIVOS|LUIZ ANTONIO FERRARO MATHIAS\" class=\"disciplinasMaterialDidatico\" href=\"javascript:void(0)\" title=\"SISTEMAS COOPERATIVOS\">SISTEMAS COOPERATIVOS</a>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <a rel=\"1052N7B|2981|2016/1|SISTEMAS DISTRIBUIDOS I|ALBERTO LUIZ FERREIRA\" class=\"disciplinasMaterialDidatico\" href=\"javascript:void(0)\" title=\"SISTEMAS DISTRIBUIDOS I\">SISTEMAS DISTRIBUIDOS I</a>\n" +
                "        </li>\n" +
                "        <li>\n" +
                "            <a rel=\"1052N7B|3245|2016/1|TRABALHO DE CONCLUSAO CURSO I - TCC|ANA CAROLINA CAETANO SENGER\" class=\"disciplinasMaterialDidatico\" href=\"javascript:void(0)\" title=\"TRABALHO DE CONCLUSAO CURSO I - TCC\">TRABALHO DE CONCLUSAO CURSO I - TCC</a>\n" +
                "        </li>\n" +
                "        </ul>\n" +
                "</form></div>\n" +
                "<br>\n" +
                "<br>\n" +
                "<div class=\"titulo_matdid\">DOCUMENTOS </div>\n" +
                "    <table class=\"tabelaComPaginacao\">\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <td class=\"colunaDownloadDocumentosMaterialDidatico\"><a href=\"http://unisanta.br/materialdidaticorm/arquivossecretaria/ProgramadeIniciacaoCientifica2016-2017a10942.pdf \" target=\"_blank\" class=\"iconeDownload\"><img src=\"/Content/imagens/iconeDownload.png\" /></a></td>\n" +
                "            <td><strong>PROGRAMA DE INICIA&#199;&#195;O CIENT&#205;FICA 2016-2017</strong><br />Bolsas CNPQ e Unisanta</td>\n" +
                "            <td class=\"colunaDataDocumentosMaterialDidatico\">07/03/2016, &#224;s 19:36</td>\n" +
                "            <td class=\"colunaTamanhoDocumentosMaterialDidatico\">184,60 KB</td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "    </table>\n" +
                "    <br />\n" +
                "\n" +
                "\n" +
                "        </article>\n" +
                "    </div>\n" +
                "    \n" +
                "    <footer>\n" +
                "    <ul>\n" +
                "        <li>UNISANTA - Universidade Santa Cecília</li>\n" +
                "        <li>Rua Oswaldo Cruz, 277 | Boqueirão - Santos/SP</li>\n" +
                "        <li>Tel: (13) 3202-7100 | FAX: (13) 3234-5297</li>\n" +
                "        <li><a href=\"/Login/PoliticaDePrivacidade\">Política de Privacidade</a><br></li>\n" +
                "    </ul>\n" +
                "    \n" +
                "    <a href=\"http://feeds.feedburner.com/unisanta\" target=\"_blank\"> <img src=\"/images/rss.png\" alt=\"RSS\"></a>\n" +
                "    <a href=\"\" target=\"_blank\"> <img src=\"/images/facebook.png\" alt=\"Fanpage da Unisanta no Facebook\"></a>\n" +
                "    <a href=\"http://twitter.com/UnisantaOficial\" target=\"_blank\"> <img src=\"/images/twitter.png\" alt=\"Twitter oficial da Unisanta\"></a>\n" +
                "    <a href=\"http://www.youtube.com/unisantaweb\" target=\"_blank\"> <img src=\"/images/youtube.png\" alt=\"Canal da Unisanta no Youtube\"></a>\n" +
                "</footer>\n" +
                "    <script src=\"/bundles/jqueryui?v=06a_8KzvBRdObcjwmGE97P8rBEqnlOiZcuhi8IXnNCA1\"></script>\n" +
                "\n" +
                "    <script src=\"/bundles/jqueryval?v=dt3XaqpEn4rFA9MkDy2QmR-5tTdUVpSHTuOr3x-Sw981\"></script>\n" +
                "\n" +
                "    <script src=\"/bundles/icheck?v=O6bA35DvGU7EnzphPxa-WRjl7qAjJIjeIZQYzkZIMS01\"></script>\n" +
                "\n" +
                "    \n" +
                "<script type=\"text/javascript\">\n" +
                "    $(document).ready(function () {\n" +
                "        //Alterna as cores entre as linhas.\n" +
                "        $(\"tr:nth-child(even)\").addClass(\"even\");\n" +
                "\n" +
                "\n" +
                "        \n" +
                "        var periodoLetivoEscolhido = \"2016/1\";\n" +
                "        $(\"#dlPeriodoLetivo\").find(\"option\").filter(function (index) {\n" +
                "            return periodoLetivoEscolhido === $(this).text();\n" +
                "        }).attr(\"selected\", \"selected\");\n" +
                "\n" +
                "        $(\"#dlPeriodoLetivo\").change(function () {\n" +
                "            window.location.href = \"/Academico/MaterialDidatico?id=\" + $(this).val();\n" +
                "        });\n" +
                "\n" +
                "        \n" +
                "        $(\".disciplinasMaterialDidatico\").click(function (event) {\n" +
                "            arquivoDisciplina = $(this).attr('rel').split(\"|\");\n" +
                "\n" +
                "            $(\"#CodTur\").val(arquivoDisciplina[0]);\n" +
                "            $(\"#CodMat\").val(arquivoDisciplina[1]);\n" +
                "            $(\"#PeriodoLetivo\").val(arquivoDisciplina[2]);\n" +
                "            $(\"#Disciplina\").val(arquivoDisciplina[3]);\n" +
                "            $(\"#Professor\").val(arquivoDisciplina[4]);\n" +
                "\n" +
                "            $(\"#fmMaterialDidatico\").submit();\n" +
                "        });\n" +
                "    });\n" +
                "</script>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <script type=\"text/javascript\">\n" +
                "        $(\".aguarde\").click(function () {\n" +
                "            $(\"#divLoading\").css(\"display\", \"block\");\n" +
                "        });\n" +
                "\n" +
                "        var _gaq = _gaq || [];\n" +
                "        _gaq.push(['_setAccount', 'UA-3620588-7']);\n" +
                "        _gaq.push(['_trackPageview']);\n" +
                "\n" +
                "        (function () {\n" +
                "            var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;\n" +
                "            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';\n" +
                "            var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);\n" +
                "        })();\n" +
                "    </script>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
    }
}
