/*
 * Copyright (c) 2018 - 2019 Cubxity, superblaubeere27 and KodingKing1
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package dev.cubxity.mc.api.data.enums

enum class Locale(val localeName: String, val internalName: String, val minecraftVersion: MinecraftVersion) {
    AF_ZA("Afrikaans", "af_za", MinecraftVersion.FIRST),
    AR_SA("Arabic", "ar_sa", MinecraftVersion.FIRST),
    AST_ES("Asturian", "ast_es", MinecraftVersion.V_1_7_4),
    AZ_AZ("Azerbaijani", "az_az", MinecraftVersion.V_1_7_10),
    BA_RU("Bashkir", "ba_ru", MinecraftVersion.V_1_14_3),
    BE_BY("Belarusian", "be_by", MinecraftVersion.V_1_9),
    BG_BG("Bulgarian", "bg_bg", MinecraftVersion.FIRST),
    BR_FR("Breton", "br_fr", MinecraftVersion.V_1_9),
    BRB("Brabantian", "brb", MinecraftVersion.V_1_13_1),
    BS_BA("Bosnian", "bs_BA", MinecraftVersion.FIRST),
    CA_ES("Catalan", "ca_es", MinecraftVersion.FIRST),
    CS_CZ("Czech", "cs_cz", MinecraftVersion.FIRST),
    CY_GB("Welsh", "cy_gb", MinecraftVersion.FIRST),
    DA_DK("Danish", "da_dk", MinecraftVersion.FIRST),
    DE_AT("Austrian German", "de_at", MinecraftVersion.V_1_10),
    DE_CH("Swiss German", "de_ch", MinecraftVersion.FIRST),
    DE_DE("German", "de_de", MinecraftVersion.FIRST),
    EL_GR("Greek", "el_gr", MinecraftVersion.FIRST),
    EN_AU("Australian English", "en_au", MinecraftVersion.FIRST),
    EN_CA("Canadian English", "en_ca", MinecraftVersion.FIRST),
    EN_GB("British English", "en_gb", MinecraftVersion.FIRST),
    EN_NZ("New Zealand English", "en_nz", MinecraftVersion.V_1_9),
    EN_PT("Pirate English", "en_pt", MinecraftVersion.FIRST),
    EN_UD("British English (upside down)", "en_ud", MinecraftVersion.V_1_9_2),
    EN_US("American English", "en_us", MinecraftVersion.FIRST),
    ENP("English puristic", "enp", MinecraftVersion.FIRST),
    EN_WS("Early Modern English (Wikipedia)", "en_ws", MinecraftVersion.FIRST),
    EO_UY("Esperanto", "eo_uy", MinecraftVersion.FIRST),
    ES_AR("Argentinian Spanish", "es_ar", MinecraftVersion.FIRST),
    ES_CL("Chilean Spanish", "es_CL", MinecraftVersion.FIRST),
    ES_ES("Spanish", "es_es", MinecraftVersion.FIRST),
    ES_MX("Mexican Spanish", "es_mx", MinecraftVersion.FIRST),
    ES_UY("Uruguayan Spanish", "es_uy", MinecraftVersion.FIRST),
    ES_VE("Venezuelan Spanish", "es_ve", MinecraftVersion.FIRST),
    ET_EE("Estonian", "et_ee", MinecraftVersion.FIRST),
    EU_ES("Basque", "eu_es", MinecraftVersion.FIRST),
    FA_IR("Persian", "fa_ir", MinecraftVersion.V_1_7_2),
    FI_FI("Finnish", "fi_fi", MinecraftVersion.FIRST),
    FIL_PH("Filipino", "fil_ph", MinecraftVersion.V_1_7_2),
    FO_FO("Faroese", "fo_fo", MinecraftVersion.V_1_9),
    FR_CA("Canadian French", "fr_ca", MinecraftVersion.FIRST),
    FR_FR("French", "fr_fr", MinecraftVersion.FIRST),
    VMF_DE("East Franconian", "vmf_de", MinecraftVersion.V_1_13_1),
    FY_NL("Frisian", "fy_nl", MinecraftVersion.V_1_9),
    GA_IE("Irish", "ga_ie", MinecraftVersion.FIRST),
    GD_GB("Scottish Gaelic", "gd_gb", MinecraftVersion.V_1_9_2),
    GL_ES("Galician", "gl_es", MinecraftVersion.FIRST),
    GOT("Gothic", "got", MinecraftVersion.V_1_14_3),
    GV_IM("Manx", "gv_im", MinecraftVersion.V_1_7_4),
    HAW("Hawaiian", "haw", MinecraftVersion.V_1_10),
    HE_IL("Hebrew", "he_il", MinecraftVersion.FIRST),
    HI_IN("Hindi", "hi_in", MinecraftVersion.FIRST),
    HR_HR("Croatian", "hr_hr", MinecraftVersion.FIRST),
    HU_HU("Hungarian", "hu_hu", MinecraftVersion.FIRST),
    HY_AM("Armenian", "hy_am", MinecraftVersion.V_1_7),
    ID_ID("Indonesian", "id_id", MinecraftVersion.FIRST),
    IG_NG("Igbo", "ig_ng", MinecraftVersion.FIRST),
    IO_EN("Ido", "io_en", MinecraftVersion.V_1_11),
    IS_IS("Icelandic", "is_is", MinecraftVersion.FIRST),
    IT_IT("Italian", "it_it", MinecraftVersion.FIRST),
    JA_JP("Japanese", "ja_jp", MinecraftVersion.FIRST),
    JBO("Lojban", "jbo", MinecraftVersion.V_1_9),
    KA_GE("Georgian", "ka_ge", MinecraftVersion.FIRST),
    KAB_DZ("Kabyle", "kab_dz", MinecraftVersion.FIRST),
    KN_IN("Kannada", "kn_in", MinecraftVersion.FIRST),
    KO_KR("Korean", "ko_kr", MinecraftVersion.FIRST),
    KSH_DE("Kölsch/Ripuarian", "ksh_de", MinecraftVersion.V_1_9),
    KW_GB("Cornish", "kw_gb", MinecraftVersion.FIRST),
    LA_VA("Latin", "la_va", MinecraftVersion.V_1_7),
    LB_LU("Luxembourgish", "lb_lu", MinecraftVersion.V_1_7),
    LI_LI("Limburgish", "li_li", MinecraftVersion.V_1_9),
    LOL_AA("LOLCAT", "lol_aa", MinecraftVersion.V_1_9),
    LT_LT("Lithuanian", "lt_lt", MinecraftVersion.FIRST),
    LV_LV("Latvian", "lv_lv", MinecraftVersion.FIRST),
    MI_NZ("Māori", "mi_nz", MinecraftVersion.V_1_7_10),
    MK_MK("Macedonian", "mk_mk", MinecraftVersion.V_1_9),
    MN_MN("Mongolian", "mn_mn", MinecraftVersion.V_1_10),
    MOH_US("Mohawk", "moh_us", MinecraftVersion.V_1_13_1),
    MS_MY("Malay", "ms_my", MinecraftVersion.FIRST),
    MT_MT("Maltese", "mt_mt", MinecraftVersion.FIRST),
    NDS_DE("Low German", "nds_de", MinecraftVersion.V_1_7_4),
    NL_BE("Dutch, Flemish", "nl_be", MinecraftVersion.FIRST),
    NL_NL("Dutch", "nl_nl", MinecraftVersion.FIRST),
    NN_NO("Norwegian Nynorsk", "nn_no", MinecraftVersion.FIRST),
    NO_NO("Norwegian", "no_no", MinecraftVersion.FIRST),
    NB_NO("Norwegian Bokmål", "nb_no", MinecraftVersion.FIRST),
    NUK("Nuu-chah-nulth", "nuk", MinecraftVersion.V_1_13_1),
    OC_FR("Occitan", "oc_fr", MinecraftVersion.V_1_7),
    OJ_CA("Ojibwe", "oj_ca", MinecraftVersion.FIRST),
    OVD_SE("Elfdalian", "ovd_se", MinecraftVersion.FIRST),
    PL_PL("Polish", "pl_pl", MinecraftVersion.FIRST),
    PT_BR("Brazilian Portuguese", "pt_br", MinecraftVersion.FIRST),
    PT_PT("Portuguese", "pt_pt", MinecraftVersion.FIRST),
    QYA_AA("Quenya (Form of Elvish from LOTR)", "qya_aa", MinecraftVersion.FIRST),
    RO_RO("Romanian", "ro_ro", MinecraftVersion.FIRST),
    RU_RU("Russian", "ru_ru", MinecraftVersion.FIRST),
    SME("Northern Sami", "sme", MinecraftVersion.V_1_8),
    SK_SK("Slovak", "sk_sk", MinecraftVersion.FIRST),
    SL_SI("Slovenian", "sl_si", MinecraftVersion.FIRST),
    SO_SO("Somali", "so_so", MinecraftVersion.V_1_9),
    SQ_AL("Albanian", "sq_al", MinecraftVersion.V_1_9),
    SR_SP("Serbian", "sr_sp", MinecraftVersion.FIRST),
    SV_SE("Swedish", "sv_se", MinecraftVersion.FIRST),
    SWG("Allgovian German", "swg", MinecraftVersion.V_1_10),
    SXU("Upper Saxon German", "sxu", MinecraftVersion.FIRST),
    SZL("Silesian", "szl", MinecraftVersion.FIRST),
    TA_IN("Tamil", "ta_IN", MinecraftVersion.FIRST),
    TH_TH("Thai", "th_th", MinecraftVersion.FIRST),
    TLH_AA("Klingon", "tlh_aa", MinecraftVersion.FIRST),
    TR_TR("Turkish", "tr_tr", MinecraftVersion.FIRST),
    TT_RU("Tatar", "tt_ru", MinecraftVersion.V_1_13_1),
    TZL_TZL("Talossan", "tzl_tzl", MinecraftVersion.V_1_9),
    UK_UA("Ukrainian", "uk_ua", MinecraftVersion.FIRST),
    CA_VAL_ES("Valencian", "ca-val_es", MinecraftVersion.V_1_7_4),
    VEC_IT("Venetian", "vec_it", MinecraftVersion.FIRST),
    VI_VN("Vietnamese", "vi_vn", MinecraftVersion.FIRST),
    YO_NG("Yoruba", "yo_ng", MinecraftVersion.FIRST),
    ZH_CN("Chinese Simplified (China)", "zh_cn", MinecraftVersion.FIRST),
    ZH_TW("Chinese Traditional (Taiwan)", "zh_tw", MinecraftVersion.FIRST);
}