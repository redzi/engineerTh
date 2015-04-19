<#macro displayUsers>
<div class="displayUsers">
    <#assign inc = 1>
    <table class="displayUsers">
      <tr>
          <th>
              user no.
          </th>
          <th>
              user name
          </th>
          <th>
              user mail
          </th>
      </tr>
      <#list users as user>
      <tr>
          <td>
              ${inc}
          </td>
          <#assign inc = inc +1>
          <td>
              ${user.name}
          </td>
          <td>
              ${user.email.address}
          </td>
      </tr>
      </#list>
     </table>
</div>
</#macro>