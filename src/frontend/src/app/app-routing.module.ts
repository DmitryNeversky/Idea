import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {IdeasResolver} from "./shared/resolvers/ideas.resolver";
import {AppComponent} from "./app.component";
import {IdeasComponent} from "./ideas/ideas.component";
import {CreateIdeaComponent} from "./create-idea/create-idea.component";

const routes: Routes = [
    {
        path: 'ideas',
        component: IdeasComponent,
        resolve: {
            ideas: IdeasResolver
        }
    },
    {
        path: 'ideas/create',
        component: CreateIdeaComponent
    }

]

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {

}