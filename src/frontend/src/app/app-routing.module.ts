import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import {IdeasResolver} from "./shared/resolvers/ideas.resolver";
import {IdeasComponent} from "./ideas/ideas.component";

const routes: Routes = [
    {
        path: '',
        component: AppComponent
    },
    {
      path: 'ideas',
      component: IdeasComponent,
      resolve: {
          ideas: IdeasResolver
      }
    },

]

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {

}