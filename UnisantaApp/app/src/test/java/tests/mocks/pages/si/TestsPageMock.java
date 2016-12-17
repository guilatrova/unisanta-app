package tests.mocks.pages.si;

import si.unisanta.tcc.unisantaapp.domain.exceptions.PageRequestFailedException;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.TestsPage;

public class TestsPageMock extends TestsPage{

    @Override
    public String getHtml() throws PageRequestFailedException {
        return "\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"pt-br\">\n" +
                "<head>\n" +
                "    <meta name=\"robots\" content=\"noindex, nofollow\" />\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <title>Portal do Aluno Unisanta - Hor&#225;rio de Provas</title>\n" +
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
                "        <p class=\"right\"><strong>Último acesso:</strong> 11/04/2016, às 17:36</p>\n" +
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
                "<h2>Horários de Provas </h2>\n" +
                "\n" +
                "<p>\n" +
                "        <button id=\"MeuHorario\" name=\"MeuHorario\" class=\"btn btn-block btn-default conteudoCalendario\"><span>Clique aqui para visualizar o calendário completo</span></button>\n" +
                "\n" +
                "</p>\n" +
                "\n" +
                "<br />\n" +
                "<br />\n" +
                "<br />\n" +
                "\n" +
                "<br />\n" +
                "\n" +
                "<form action=\"/Conteudo/HorariosDeProvas\" id=\"fmHorariosDeProvas\" method=\"post\"><input name=\"__RequestVerificationToken\" type=\"hidden\" value=\"I1GH9-FWN2TfWvocj8OdfMWHVtJuXcJEPr7p1Zj9mu__nFUXVZw6Hel2VKpFTbGMhp7OVrjNWkAndGlUtRq1G4-PYJAYL_FxzRmN1xSUOYU1\" />    <span class=\"notprint\">\n" +
                "        \n" +
                "        Escolha:\n" +
                "        <br>\n" +
                "            <input type=\"radio\" name=\"prova\" value=\"\" data-id=\"T\" checked=\"checked\" />\n" +
                "        Todas\n" +
                "      \n" +
                "\n" +
                "    <input type=\"radio\" name=\"prova\" value=\"P1\" data-id=\"P1\" />P1\n" +
                "\n" +
                "\n" +
                "        \n" +
                "        \n" +
                "\n" +
                "</span>\n" +
                "    <br>\n" +
                "    <br />\n" +
                "    <p><strong>Curso: </strong>SISTEMAS DE INFORMA&#199;&#195;O (Noturno)</p>\n" +
                "    <table id=\"tbHorarioProva\" border=\"0\">\n" +
                "        <thead>\n" +
                "            <tr>\n" +
                "                <th style=\"width:8px;\">Per.</th>\n" +
                "                <th style=\"width:12px;\">Hor.</th>\n" +
                "                <th style=\"width:300px;\">Disciplinas</th>\n" +
                "                <th style=\"width:130px;\">Professor</th>\n" +
                "                <th style=\"width:130px;\"></th>\n" +
                "                <th style=\"width:50px;\">Turma</th>\n" +
                "                <th style=\"width:255px;\">Local</th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <tbody>\n" +
                "\n" +
                "\n" +
                "                    <tr>\n" +
                "                        <td colspan=\"7\" style=\"background:#ddd; text-align:left !important; color:#111; font-size: 16px !important; font-weight:700; padding:10px;\">Segunda-Feira - P1  - Prova: 04/04/2016</td>\n" +
                "                    </tr>\n" +
                "                <tr>\n" +
                "                    <td>7º</td>\n" +
                "                    <td>21:00</td>\n" +
                "                    <td>\n" +
                "&#201;TICA MEIO AMB. E SUSTENTAB.                        - P1 Teoria\n" +
                "                    </td>\n" +
                "                    <td>Cordella</td>\n" +
                "                    <td></td>\n" +
                "                    <td>\n" +
                "                        1052N7B                       </td>\n" +
                "                    <td>Sala 34 Bloco D 3&#186; Andar </td>\n" +
                "                </tr>\n" +
                "                    <tr>\n" +
                "                        <td colspan=\"7\" style=\"background:#ddd; text-align:left !important; color:#111; font-size: 16px !important; font-weight:700; padding:10px;\">Ter&#231;a-Feira - P1  - Prova: 05/04/2016</td>\n" +
                "                    </tr>\n" +
                "                <tr>\n" +
                "                    <td>7º</td>\n" +
                "                    <td>19:30</td>\n" +
                "                    <td>\n" +
                "GEST&#195;O DA INFORMA&#199;&#195;O                        - P1 Teoria\n" +
                "                    </td>\n" +
                "                    <td>Cordella</td>\n" +
                "                    <td></td>\n" +
                "                    <td>\n" +
                "                        1052N7B                       </td>\n" +
                "                    <td>Sala 31 Bloco D 3&#186; Andar </td>\n" +
                "                </tr>\n" +
                "                    <tr>\n" +
                "                        <td colspan=\"7\" style=\"background:#ddd; text-align:left !important; color:#111; font-size: 16px !important; font-weight:700; padding:10px;\">Quarta-Feira - P1  - Prova: 06/04/2016</td>\n" +
                "                    </tr>\n" +
                "                <tr>\n" +
                "                    <td>7º</td>\n" +
                "                    <td>21:00</td>\n" +
                "                    <td>\n" +
                "PESQUISA OPERAC. I                        - P1 Teoria\n" +
                "                    </td>\n" +
                "                    <td>Avelino</td>\n" +
                "                    <td></td>\n" +
                "                    <td>\n" +
                "                        1052N7B                       </td>\n" +
                "                    <td>Sala 32 Bloco D 3&#186; Andar </td>\n" +
                "                </tr>\n" +
                "                    <tr>\n" +
                "                        <td colspan=\"7\" style=\"background:#ddd; text-align:left !important; color:#111; font-size: 16px !important; font-weight:700; padding:10px;\">Quinta-Feira - P1  - Prova: 07/04/2016</td>\n" +
                "                    </tr>\n" +
                "                <tr>\n" +
                "                    <td>7º</td>\n" +
                "                    <td>19:30</td>\n" +
                "                    <td>\n" +
                "PRAT. E GERENC. DE PROJETOS I                        - P1 Teoria\n" +
                "                    </td>\n" +
                "                    <td>Guerra</td>\n" +
                "                    <td></td>\n" +
                "                    <td>\n" +
                "                        1052N7B                       </td>\n" +
                "                    <td>Sala 32 Bloco D 3&#186; Andar </td>\n" +
                "                </tr>\n" +
                "                    <tr>\n" +
                "                        <td colspan=\"7\" style=\"background:#ddd; text-align:left !important; color:#111; font-size: 16px !important; font-weight:700; padding:10px;\">Sexta-Feira - P1  - Prova: 08/04/2016</td>\n" +
                "                    </tr>\n" +
                "                <tr>\n" +
                "                    <td>7º</td>\n" +
                "                    <td>21:00</td>\n" +
                "                    <td>\n" +
                "SIST. DISTRIBUIDOS I                        - P1 Teoria\n" +
                "                    </td>\n" +
                "                    <td>Alberto</td>\n" +
                "                    <td></td>\n" +
                "                    <td>\n" +
                "                        1052N7B                       </td>\n" +
                "                    <td>Sala 36 Bloco D 3&#186; Andar </td>\n" +
                "                </tr>\n" +
                "                    <tr>\n" +
                "                        <td colspan=\"7\" style=\"background:#ddd; text-align:left !important; color:#111; font-size: 16px !important; font-weight:700; padding:10px;\">Ter&#231;a-Feira - P1  - Prova: 12/04/2016</td>\n" +
                "                    </tr>\n" +
                "                <tr>\n" +
                "                    <td>7º</td>\n" +
                "                    <td>21:00</td>\n" +
                "                    <td>\n" +
                "SISTEMAS COOPERATIVOS                        - P1 Teoria\n" +
                "                    </td>\n" +
                "                    <td>Luiz Antonio</td>\n" +
                "                    <td></td>\n" +
                "                    <td>\n" +
                "                        1052N7B                       </td>\n" +
                "                    <td>Sala 31 Bloco D 3&#186; Andar</td>\n" +
                "                </tr>\n" +
                "                    <tr>\n" +
                "                        <td colspan=\"7\" style=\"background:#ddd; text-align:left !important; color:#111; font-size: 16px !important; font-weight:700; padding:10px;\">Quarta-Feira - P1  - Prova: 13/04/2016</td>\n" +
                "                    </tr>\n" +
                "                <tr>\n" +
                "                    <td>7º</td>\n" +
                "                    <td>21:00</td>\n" +
                "                    <td>\n" +
                "INTERFACE HOMEM  M&#193;QUINA                        - P1 Teoria\n" +
                "                    </td>\n" +
                "                    <td>Franciele</td>\n" +
                "                    <td></td>\n" +
                "                    <td>\n" +
                "                        1052N7B                       </td>\n" +
                "                    <td>Sala 612 Bloco E 6&#186; Andar</td>\n" +
                "                </tr>\n" +
                "                    <tr>\n" +
                "                        <td colspan=\"7\" style=\"background:#ddd; text-align:left !important; color:#111; font-size: 16px !important; font-weight:700; padding:10px;\">Sexta-Feira - P1  - Prova: 15/04/2016</td>\n" +
                "                    </tr>\n" +
                "                <tr>\n" +
                "                    <td>7º</td>\n" +
                "                    <td>21:00</td>\n" +
                "                    <td>\n" +
                "AUDITORIA E SEG. DA INFORM. I                        - P1 Teoria\n" +
                "                    </td>\n" +
                "                    <td>Luiz Antonio</td>\n" +
                "                    <td></td>\n" +
                "                    <td>\n" +
                "                        1052N7B                       </td>\n" +
                "                    <td>Sala 36 Bloco D 3&#186; Andar</td>\n" +
                "                </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "</form>\n" +
                "    <div class=\" imprimir\">\n" +
                "        <a href=\"javascript:window.print()\">\n" +
                "            <i class=\"icon-print\"></i>\n" +
                "            <br>\n" +
                "            imprimir\n" +
                "        </a>\n" +
                "    </div>\n" +
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
                "\n" +
                "    <script type=\"text/javascript\">\n" +
                "        $(document).ready(function () {\n" +
                "\n" +
                "            $(\"#MeuHorario\").click(function () {\n" +
                "\n" +
                "               var url = \"/Academico/HorariosDeProvas?aluno=N&DescricaoEtapa=\";\n" +
                "\n" +
                "                $(location).attr('href', url);\n" +
                "\n" +
                "            });\n" +
                "\n" +
                "\n" +
                "            $(':radio').change(function (event) {\n" +
                "                var id = $(this).data('DescricaoEtapa');\n" +
                "\n" +
                "                    var url = \"/Academico/HorariosDeProvas?aluno=S&DescricaoEtapa=\" + $(this).val();\n" +
                "\n" +
                "                $(location).attr('href', url);\n" +
                "            });\n" +
                "        });\n" +
                "    </script>\n" +
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
